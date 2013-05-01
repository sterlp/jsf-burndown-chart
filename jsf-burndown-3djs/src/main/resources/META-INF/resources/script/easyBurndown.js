if (!window.easy) {
    var easy = window.easy = {};
}
// see http://courses.coreservlets.com/Course-Materials/pdf/jsf/jsf2/JSF2-Composite-Components-4.pdf
easy.escapeColons = function(string) {
    return(string.replace(/:/g, "\\:"));
};
easy.renderBurnDown = function(inD3, inData, inSelector) {
    var d3 = inD3,
        parseDate = d3.time.format("%Y-%m-%d").parse,
        startDate = parseDate(inData.start),
        planedHours = inData.planedHours,
        endDate = parseDate(inData.end),
        data = inData.burndowns,
        timeDomain = inData.timeDomain,
        parsedTimeDomain = [],
        selector = easy.escapeColons(inSelector);
    
    var margin = {top: 20, right: 20, bottom: 30, left: 50},
        width = (40 * timeDomain.length) - margin.left - margin.right,
        height = (inData.planedHours * 2.5) - margin.top - margin.bottom;

    if (width < 200) width = 400;
    if (height < 200) height = 400;

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