(function() {
  var callWithJQuery;

  callWithJQuery = function(pivotModule) {
    if (typeof exports === "object" && typeof module === "object") {
      return pivotModule(require("jquery"));
    } else if (typeof define === "function" && define.amd) {
      return define(["jquery"], pivotModule);
    } else {
      return pivotModule($);
    }
  };

  callWithJQuery(function($) {
    var drawECharts;
    drawECharts = function(chartType, extraOptions) {
      return function(pivotData, opts) {
        var agg, base, base1, colKey, colKeys,
          dataArray, dataTable, defaults, fullAggName,
          groupByTitle, h, hAxisTitle, headers, i, j, len, len1,
          numCharsInHAxis, options, ref, result, row, rowKey, rowKeys,
          title, tree2, vAxisTitle, val, wrapper, x, y;

        defaults = {
          localeStrings: {},
          echarts: {}
        };
        opts = $.extend(true, {}, defaults, opts);


        rowKeys = pivotData.getRowKeys();
        if (rowKeys.length === 0) {
          rowKeys.push([]);
        }
        colKeys = pivotData.getColKeys();
        if (colKeys.length === 0) {
          colKeys.push([]);
        }
        //////////// create title
        fullAggName = pivotData.aggregatorName;
        if (pivotData.valAttrs.length) {
          fullAggName += "(" + (pivotData.valAttrs.join(", ")) + ")";
        }
        /////////// create header
        headers = (function() {
          var i, len, results;
          results = [];
          for (i = 0, len = rowKeys.length; i < len; i++) {
            h = rowKeys[i];
            results.push(h.join("-"));
          }
          return results;
        })();

        headers.unshift("");
        // case 1 : --------------------------------------
        dataArray = [];
        ref = pivotData.tree;
        for (y in ref) {
          tree2 = ref[y];
          for (x in tree2) {
            agg = tree2[x];
            dataArray.push([parseFloat(x), parseFloat(y), fullAggName + ": \n" + agg.format(agg.value())]);
          }
        }
        hAxisTitle = pivotData.colAttrs.join("-");
        vAxisTitle = pivotData.rowAttrs.join("-");
        title = "";
        // case 2 : --------------------------------------
        dataArray = [headers];
        for (i = 0, len = colKeys.length; i < len; i++) {
          colKey = colKeys[i];
          row = [colKey.join("-")];
          numCharsInHAxis += row[0].length;
          for (j = 0, len1 = rowKeys.length; j < len1; j++) {
            rowKey = rowKeys[j];
            agg = pivotData.getAggregator(rowKey, colKey);
            if (agg.value() != null) {
              val = agg.value();
              if ($.isNumeric(val)) {
                if (val < 1) {
                  row.push(parseFloat(val.toPrecision(3)));
                } else {
                  row.push(parseFloat(val.toFixed(3)));
                }
              } else {
                row.push(val);
              }
            } else {
              row.push(null);
            }
          }
          dataArray.push(row);
        }
        title = vAxisTitle = fullAggName;
        hAxisTitle = pivotData.colAttrs.join("-");
        if (hAxisTitle !== "") {
          title += " VS " + hAxisTitle;
        }
        groupByTitle = pivotData.rowAttrs.join("-");
        if (groupByTitle !== "") {
          title += " BY " + groupByTitle;
        }


        options = {
          title: {
            text: title
          },
          tooltip: {},
          legend: {
            data:['Sales']
          },
          xAxis: {
            title: hAxisTitle,
            data: ["shirt","cardign","chiffon shirt","pants","heels","socks"]
          },
          yAxis: {
            title: vAxisTitle
          },
          series: [{
            name: 'Sales',
            type: 'bar',
            data: [5, 20, 36, 10, 10, 20]
          }]
        };
        if (chartType === "line") {
          //console.log('Chart bar');
        }
        options = $.extend(true, {}, options, opts.echart, extraOptions);

        var newDiv = document.createElement("div");
        var myChart = echarts.init(newDiv);
        // use configuration item and data specified to show chart
        myChart.setOption(options);
        return result;
      };
    };
    return $.pivotUtilities.echarts_renderers = {
      "Line EChart": drawECharts("line"),
      "Bar EChart": drawECharts("bar"),
      "Stacked Bar EChart": drawECharts("bar", {
        isStacked: true
      })
    };
  });

}).call(this);

//# sourceMappingURL=gchart_renderers.js.map
