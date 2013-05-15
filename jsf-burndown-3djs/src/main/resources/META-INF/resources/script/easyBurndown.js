if (!window.easy) {
    var easy = window.easy = {};
}
easy.isNumber = function(n) {
  return !isNaN(parseFloat(n)) && isFinite(n);
}
// see http://courses.coreservlets.com/Course-Materials/pdf/jsf/jsf2/JSF2-Composite-Components-4.pdf
easy.escapeColons = function(string) {
    return(string.replace(/:/g, "\\:"));
};

/** http://www.d3noob.org/2013/01/adding-grid-lines-to-d3js-graph.html */
easy.make_x_axis = function(d3, x, ticks) {
    if (!ticks) ticks = 10;
    return d3.svg.axis()
        .scale(x)
         .orient("bottom")
         .ticks(ticks);
};
easy.make_y_axis = function(d3, y, ticks) {        
    if (!ticks) ticks = 10;
    return d3.svg.axis()
        .scale(y)
        .orient("left")
        .ticks(ticks);
};
easy.renderBurnDown = function(config, inData, inSelector) {
    var d3 = config.d3 || d3,
        parseDate = d3.time.format("%Y-%m-%d").parse,
        startDate = parseDate(inData.start),
        planedHours = inData.planedHours,
        endDate = parseDate(inData.end),
        data = inData.burndowns,
        timeDomain = inData.timeDomain,
        parsedTimeDomain = [],
        selector = easy.escapeColons(inSelector),
        // default width and height, based on the given data
        width = (40 * timeDomain.length),
        height = (inData.planedHours * 2),
        showGrid = config.config || true;

    if (easy.isNumber(config.width) && config.width > 0) width = config.width;
    if (easy.isNumber(config.height) && config.height > 0) height = config.height;

    var margin = {top: 20, right: 20, bottom: 30, left: 50},
        width = width - margin.left - margin.right,
        height = height - margin.top - margin.bottom;
    // make sure we are not to small
    if (width < 100) width = 100;
    if (height < 100) height = 100;

    var parseDate = d3.time.format("%Y-%m-%d").parse;
    // parse dates in data
    for (var i = 0; i < data.length; ++i) {
      var d = data[i], old = d.date;
      d.date = parseDate(d.date);
      if (!d.date) throw "Failed to parse date in burndowns --> " + old;
    }
    // parse dates in data
    for (var i = 0; i < timeDomain.length; ++i) {
      var d = timeDomain[i], old = d;
      d = parseDate(d);
      if (!d) throw "Failed to parse date in time domain --> " + old;
      parsedTimeDomain.push(d);
    }
    timeDomain = parsedTimeDomain;

    var x = d3.time.scale();
    var y = d3.scale.linear()
              .range([height, 0]);

    var xAxis = d3.svg.axis()
        .scale(x)
        .orient("bottom")
        .ticks(function() { return timeDomain; });

    var yAxis = d3.svg.axis()
        .scale(y)
        .orient("left");

    var line = d3.svg.line()
        .x(function(d) { return x(d.date); })
        .y(function(d) { return y(d.hours); });

    var svg = d3.select(selector).append("svg:svg")
        .attr("width", width + margin.left + margin.right)
        .attr("height", height + margin.top + margin.bottom)
        .append("g")
        .attr("transform", "translate(" + margin.left + "," + margin.top + ")");


    // calculate the output range for the x axis.
    // input range is timeDomain.
    var timeOutputRange = [];
    var currentX = 0;
    var diff = width / (timeDomain.length - 1);
    for (var i = 0; i < timeDomain.length; i++) {
        timeOutputRange.push(currentX);
        currentX += diff;
    }
    x.range(timeOutputRange);
    x.domain(timeDomain);
    y.domain([0, d3.max(data, function(d) { return d.hours; })]);
    
    // append grid
    if (showGrid) {
        svg.append("g")         
            .attr("class", "grid")
            .attr("transform", "translate(0," + height + ")")
            .call(easy.make_x_axis(d3, x, function() { return timeDomain; })
                .tickSize(-height, 0, 0)
                .tickFormat("")
            );

        svg.append("g")         
            .attr("class", "grid")
            .call(easy.make_y_axis(d3, y)
                .tickSize(-width, 0, 0)
                .tickFormat("")
            );
    }
    
    // append axis
    svg.append("g")
        .attr("class", "x axis")
        .attr("transform", "translate(0," + height + ")")
        .call(xAxis);

    svg.append("g")
        .attr("class", "y axis")
        .call(yAxis)
      .append("text")
        .attr("y", "-1em")
        .attr("x", 0)
        .style("text-anchor", "end")
        .text("Hours");

    // take the first data point and the date of the last point
    var ideal = [{date: startDate, hours: planedHours},{date: endDate, hours: 0}];
    svg.append("path")
        .datum(ideal)
        .attr("class", "ideal")
        .attr("d", line);


    // remove any data points, that have no data yet
    for (var i = data.length - 1; i >= 0; i--) {
      if (data[i].hours === -1) {
        data.splice(i, 1);
      }
    }
    svg.append("path")
        .datum(data)
        .attr("class", "line")
        .attr("d", line);

};