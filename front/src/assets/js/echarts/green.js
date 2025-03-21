(function (root, factory) {if (typeof define === 'function' && define.amd) {
        // AMD. Register as an anonymous module.
        define(['exports', 'echarts'], factory);
    } else if (typeof exports === 'object' && typeof exports.nodeName !== 'string') {
        // CommonJS
        factory(exports, require('echarts'));
    } else {
        // Browser globals
        factory({}, root.echarts);
    }
}(this, function (exports, echarts) {
    var log = function (msg) {
        if (typeof console !== 'undefined') {
            console && console.error && console.error(msg);
        }
    };
    if (!echarts) {
        log('ECharts is not Loaded');
        return;
    }

    var colorPalette = [
        '#b2d234',
        '#37bc9b',
        '#009fa6',
        '#a9cba2',
        '#397b29',
        '#8abb6f',
        '#759c6a',
        '#bfd3b7'
    ];

    var theme = {

        color: colorPalette,

        title: {
            textStyle: {
                "fontSize": 13,
                "color" : "#515253"
            }
        },

        visualMap: {
            color:['408829','#a9cba2']
        }, 

        toolbox: {
            color : ['#408829','#408829','#408829','#408829']
        },

   tooltip: {
        backgroundColor: 'rgba(0,0,0,0.5)',
        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
            type : 'line',         // 默认为直线，可选为：'line' | 'shadow'
            lineStyle : {          // 直线指示器样式设置
                color: '#408829',
                type: 'dashed'
            },
            crossStyle: {
                color: '#408829'
            },
            shadowStyle : {                     // 阴影指示器样式设置
                color: 'rgba(200,200,200,0.3)'
            }
        }
    },

    // 区域缩放控制器
    dataZoom: {
        dataBackgroundColor: '#eee',            // 数据背景颜色
        fillerColor: 'rgba(64,136,41,0.2)',   // 填充颜色
        handleColor: '#408829'     // 手柄颜色
    },

	grid: {
        borderWidth: 0
    },

	categoryAxis: { 
        axisLine: {            // 坐标轴线
            lineStyle: {       // 属性lineStyle控制线条样式
                color: '#666666'
            }
        },
        splitLine: {           // 分隔线
            lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                color: ['#eee']
            }
        }
    },

	valueAxis: { 
        axisLine: {            // 坐标轴线
            lineStyle: {       // 属性lineStyle控制线条样式
                color: '#666666'
            }
        },
        splitArea : {
            show : true,
            areaStyle : {
                color: ['rgba(250,250,250,0.1)','rgba(200,200,200,0.1)']
            }
        },
        splitLine: {           // 分隔线
            lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                color: ['#eee']
            }
        }
    },

    timeline : {
        lineStyle : {
            color : '#408829'
        },
        controlStyle : {
            normal : { color : '#408829'},
            emphasis : { color : '#408829'}
        }
    },

	k: {
        itemStyle: {
            normal: {
                color: '#68a54a',          // 阳线填充颜色
                color0: '#a9cba2',      // 阴线填充颜色
                lineStyle: {
                    width: 1,
                    color: '#408829',   // 阳线边框颜色
                    color0: '#86b379'   // 阴线边框颜色
                }
            }
        }
    },

	force : { 
        itemStyle: {
            normal: {
                linkStyle : {
                    color : '#408829'
                }
            }
        }
    },

   chord : { 
        padding : 4,
        itemStyle : {
            normal : {
                borderWidth: 1,
                borderColor: 'rgba(128, 128, 128, 0.5)',
                chordStyle : {
                    lineStyle : {
                        color : 'rgba(128, 128, 128, 0.5)'
                    }
                }
            },
            emphasis : {
                borderWidth: 1,
                borderColor: 'rgba(128, 128, 128, 0.5)',
                chordStyle : {
                    lineStyle : {
                        color : 'rgba(128, 128, 128, 0.5)'
                    }
                }
            }
        }
    },

        candlestick: { /**/
            itemStyle: {
                normal: {
                    color: '#408829',
                    color0: '#a9cba2',
                    lineStyle: {
                        width: 1,
                        color: '#408829',
                        color0: '#a9cba2'
                    }
                }
            }
        },

        graph: { 
            color: colorPalette
        },

        map: {
        itemStyle: {
            normal: {
                areaStyle: {
                    color: '#ddd'
                },
                label: {
                    textStyle: {
                        color: '#c12e34'
                    }
                }
            },
            emphasis: {                 // 也是选中样式
                areaStyle: {
                    color: '#99d2dd'
                },
                label: {
                    textStyle: {
                        color: '#c12e34'
                    }
                }
            }
        }
    },
    gauge : {
        "axisLine": {
            "show": true,
            "lineStyle": {
                "color": [
                    [0.2, "#f44336"],
                    [0.8, "#48b"],
                    [1, "#4ac6a8"]],
                "width": 8
            }
        },
        axisTick: {            // 坐标轴小标记
            splitNumber: 10,   // 每份split细分多少段
            length :12,        // 属性length控制线长
            lineStyle: {       // 属性lineStyle控制线条样式
                color: 'auto'
            }
        },
        axisLabel: {           // 坐标轴文本标签，详见axis.axisLabel
            textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                color: 'auto'
            }
        },
        "splitLine": {           // 分隔线
            "length" : 18,         // 属性length控制线长
            "lineStyle": {       // 属性lineStyle（详见lineStyle）控制线条样式
                "color": 'auto',
                "fontSize": 8
            }
        },
        "pointer" : {
            "width":4,
            "length" : '80%',
            "color" : 'auto'
        },
        "detail" : {
            "textStyle": {
                "color": 'auto',
                "fontSize": 8
            }
        }
    }
  };
    echarts.registerTheme('green', theme);
}));