import * as requestApi from "./network/dashboardRequestApi.js";

(async function updateData(){
    const numberOfUncomMeters = document.getElementById("numberOfUncomMeters");
    const numberOfMeters = document.getElementById("numberOfMeters");
    const leastUptake = document.getElementById("leastUptake");

    let averagePercentage = 0;
    let uncomPercentageRounded = 0;
    let acceptPercentageRounded = 0;

    const options = {
        series: [0],
        chart: {
            height: 230,
            type: "radialBar"
        },
        plotOptions: {
            radialBar: {
                hollow: {
                    margin: 0,
                    size: "60%"
                },
                track: {
                    background: '#DFEEDB'
                },
                dataLabels: {
                    showOn: "always",
                    name: {
                        show: false,
                    },
                    value: {
                        color: '#4C9A79',
                        fontSize: "30px",
                        show: true
                    }
                }
            }
        },
        grid: {
            padding: {
                top: -30,
                bottom: -30,
                left: -30,
                right: -30,
            }
        },
        stroke: {
            lineCap: "round",
        },
        colors: ['#4C9A79']
    };

    const acceptableGauge = new ApexCharts(document.querySelector('#acceptable'), options);
    acceptableGauge.render();

    const uncommisionedGauge= new ApexCharts(document.querySelector('#uncommissioned'), options);
    uncommisionedGauge.render();

    const percentageGauge= new ApexCharts(document.querySelector('#percentage'), options);
    percentageGauge.render();

    try {
        const numberOfUncomMetersValue = await requestApi.getTotalUncommissionedAmount();
        const numberOfMetersValue = await requestApi.getTotalAmount();
        const leastUptakeValue = await requestApi.getLowestUptakeArea();
        const acceptableUptakeValue = await requestApi.getAcceptableUptakePercentage();
        const averageUptakeValue = await requestApi.getAverageUptakePercentage();

        const uncomPercentage = numberOfUncomMetersValue / numberOfMetersValue * 100;
        const acceptPercentage = acceptableUptakeValue / 2269 * 100

        averagePercentage = averageUptakeValue * 100
        uncomPercentageRounded = uncomPercentage.toFixed(0);
        acceptPercentageRounded = acceptPercentage.toFixed(0);

        numberOfUncomMeters.textContent = numberOfUncomMetersValue;
        numberOfMeters.textContent = numberOfMetersValue;
        leastUptake.textContent = leastUptakeValue;

        acceptableGauge.updateSeries([acceptPercentageRounded]);
        uncommisionedGauge.updateSeries([uncomPercentageRounded]);
        percentageGauge.updateSeries([averagePercentage]);

    } catch (error) {
        console.log(error);
    }
})()
