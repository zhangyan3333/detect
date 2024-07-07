<template>
    <div>
        <Row :gutter="5">
            <Col flex="6">
                <Card title="基本信息" dis-hover>
                    <div style="height:180px;width: 100%;font-size: 18px;font-weight: bold;color: #4853bd;">
                        {{ '系统: '+ systemInfo.osName }}
                        <br>
                        {{ 'CPU核心数: '+ systemInfo.cpuCount }}
                        <br>
                        {{ '内存: '+ formatSize(systemInfo.totalMemorySize) }}
                        <br>
                        {{ '硬盘: '+ formatSize(systemInfo.totalDiskSize) }}
                    </div>
                </Card>
            </Col>
            <Col flex="6">
                <Card title="CPU占用率" dis-hover>
                    <div ref="cpuCurve" style="height:180px;width: 100%;"></div>
                </Card>
            </Col>
            <Col flex="6">
                <Card title="内存使用情况" dis-hover>
                    <div ref="memoryCurve" style="height:180px;width: 100%;"></div>
                </Card>
            </Col>
            <Col flex="6">
                <Card title="硬盘使用情况" dis-hover>
                    <div ref="diskCurve" style="height:180px;width: 100%;"></div>
                </Card>
            </Col>
        </Row>
        <Row style="margin-top: 5px">
            <Col flex="auto">
                <Card title="访问量" dis-hover>
                    <div ref="requestCurve" style="height:400px;width: 100%;"></div>
                </Card>
            </Col>
        </Row>
    </div>
</template>

<script>
    import { mapState, mapActions } from 'vuex';
    import dayjs from 'dayjs';
    import { entityRequest } from '@/libs/system/requestUtil';
    import * as echarts from 'echarts';


    export default {
        name: 'setting-system',
        data() {
            return {
                apiBasePath: 'systemInfo',
                tableLoading: false,
                systemInfo: {},
                cpuChart: {},
                cpuChartOption: {
                    series: [{
                        data: []
                    }]
                },
                memoryChart: {},
                memoryChartOption: {
                    series: [{
                        data: []
                    }]
                },
                diskChart: {},
                diskChartOption: {
                    series: [{
                        data: []
                    }]
                },
                requestChart: {},
                requestChartOption: {
                    series: [{
                        data: []
                    }]
                },
            }
        },
        mounted() {
            this.initializeChart();
            this.query();
        },
        beforeDestroy () {
            if (this.memoryChart) {
                this.memoryChart.dispose();
                this.memoryChart = null;
            }
        },
        computed: {
            ...mapState('admin/user', [
                'info'
            ])
        },
        methods: {
            query() {
                this.tableLoading = true;
                entityRequest('get', this.apiBasePath, undefined,
                    // 第四个参数, onSuccess
                    (response) => {
                        this.systemInfo = response.data;
                        this.refreshChart();
                    },
                    // 第五个参数, onFinish
                    () => {
                        this.tableLoading = false;
                    });
            },
            initializeChart (){
                let that=this;
                this.cpuChartOption = {
                    series: [
                        {
                            type: 'gauge',
                            radius: '90%',
                            startAngle: 90,
                            endAngle: -270,
                            pointer: {
                                show: false
                            },
                            progress: {
                                show: true,
                                overlap: false,
                                clip: false,
                                itemStyle: {
                                    color: '#00D000'
                                }
                            },
                            axisLine: {
                                lineStyle: {
                                    width: 20
                                }
                            },
                            splitLine: {
                                show: false
                            },
                            axisTick: {
                                show: false
                            },
                            axisLabel: {
                                show: false
                            },
                            data: [{
                                value: 0,
                                detail: {
                                    valueAnimation: true,
                                    offsetCenter: ['0%', '0%']
                                }
                            }],
                            title: {
                                show: false
                            },
                            detail: {
                                fontSize: 20,
                                fontWeight: 'bold',
                                color: '00D000',
                                formatter: '{value}%'
                            }
                        }
                    ]
                };
                this.cpuChart = echarts.init(this.$refs.cpuCurve);
                this.cpuChart.setOption(this.cpuChartOption);

                this.memoryChartOption = {
                    series: [
                        {
                            type: 'pie',
                            radius: '90%',
                            data: [],
                            label: {
                                fontSize: '18',
                                fontWeight: 'bold',
                                formatter: function (params) {
                                    return params.name+'\n'+that.formatSize(params.value);
                                },
                                position: 'inside'
                            },
                            emphasis: {
                                itemStyle: {
                                    shadowBlur: 20,
                                    shadowOffsetX: 5,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                };
                this.memoryChart = echarts.init(this.$refs.memoryCurve);
                this.memoryChart.setOption(this.memoryChartOption);


                this.diskChartOption = {
                    series: [
                        {
                            type: 'pie',
                            radius: '90%',
                            data: [],
                            label: {
                                fontSize: '18',
                                fontWeight: 'bold',
                                formatter: function (params) {
                                    return params.name+'\n'+that.formatSize(params.value);
                                },
                                position: 'inside'
                            },
                            emphasis: {
                                itemStyle: {
                                    shadowBlur: 20,
                                    shadowOffsetX: 5,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                };
                this.diskChart = echarts.init(this.$refs.diskCurve);
                this.diskChart.setOption(this.diskChartOption);

                this.requestChartOption = {
                    xAxis: {
                        type: 'time',
                        axisLabel:{
                            formatter:{
                                year: '{yyyy}',
                                month: '{MMM}',
                                day: '{d}',
                                hour: '{HH}:{mm}',
                                minute: '{HH}:{mm}',
                                second: '{HH}:{mm}:{ss}',
                                millisecond: '{HH}:{mm}:{ss}'
                            }
                        }
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [
                        {
                            symbol: 'none',
                            sampling: 'lttb',
                            emphasis: false,
                            data: [],
                            type: 'line'
                        }
                    ],
                    dataZoom: [{
                        type: 'inside',
                        xAxisIndex: 0,
                        minValueSpan: 1000 * 60,
                        filterMode: 'filter',
                        start: 0,
                        end: 100
                    },{
                        type: 'slider',
                        xAxisIndex:0,
                        minValueSpan: 1000 * 60,
                        filterMode: 'filter',
                        start :0,
                        end :100,
                        brushSelect:false
                    }],
                    tooltip: {
                        trigger: 'axis'
                    }
                };
                this.requestChart = echarts.init(this.$refs.requestCurve);
                this.requestChart.setOption(this.requestChartOption);
            },
            refreshChart() {
                let color = this.systemInfo.cpuLoad < 0.3 ? '#00D000' : this.systemInfo.cpuLoad < 0.7 ? '#FFB000' : '#FF0000';
                this.cpuChartOption.series[0].data[0].value = (this.systemInfo.cpuLoad * 100).toFixed(0);
                this.cpuChartOption.series[0].progress.itemStyle.color = color;
                this.cpuChartOption.series[0].detail.color = color;
                this.cpuChart.setOption(this.cpuChartOption);

                this.memoryChartOption.series[0].data = [
                    {
                        value: this.systemInfo.totalMemorySize - this.systemInfo.freeMemorySize,
                        name: '已用'
                    },
                    {
                        value: this.systemInfo.freeMemorySize,
                        name: '剩余'
                    }
                ];
                this.memoryChart.setOption(this.memoryChartOption, true);

                this.diskChartOption.series[0].data = [
                    {
                        value: this.systemInfo.totalDiskSize - this.systemInfo.freeDiskSize,
                        name: '已用'
                    },
                    {
                        value: this.systemInfo.freeDiskSize,
                        name: '剩余'
                    }
                ];
                this.diskChart.setOption(this.diskChartOption, true);

                this.requestChartOption.series[0].data = this.generateRequestChartDate(this.systemInfo.requestCounts, this.systemInfo.requestCountIndex);
                this.requestChart.setOption(this.requestChartOption, true);
            },
            generateRequestChartDate(data,startIndex) {
                let baseTime = new Date().valueOf() - 86400000;
                let requestCounts = [];
                for (let i = startIndex; i < data.length; i++) {
                    requestCounts.push([new Date(baseTime),data[i]]);
                    baseTime+=60000;
                }
                for (let i = 0; i < startIndex; i++) {
                    requestCounts.push([new Date(baseTime),data[i]]);
                    baseTime+=60000;
                }
                return requestCounts;
            },
            formatSize(size) {
                if (size > 1099511627776) {
                    return (size / 1099511627776).toFixed(2) + " TB";
                } else {
                    return (size / 1073741824).toFixed(1) + " GB";
                }
            }
        }
    }
</script>