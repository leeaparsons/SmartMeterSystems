import * as mapRequestApi from "../network/mapRequestApi.js";
import * as dashboardRequestApi from "../network/dashboardRequestApi.js";

const assert = chai.assert;
const expect = chai.expect;

// test mapRequestApi
describe("mapRequestApi", function () {
    describe("#paintMapCommissioned", function () {
        let result;
        before(async function () {
            result = await mapRequestApi.paintMapCommissioned();
            if (!result) {
                this.skip(); // <= skips entire describe
            }
        });

        it("response success, returns data as an array", () => {
            expect(result).to.be.an("array");
        });

        it("data array is not empty", () => {
            assert.isNotEmpty(result);
        });

        it("data array is in the correct format", () => {
            assert.containsAllKeys(result[0], [
                "postcodeArea",
                "commissionedPercentage",
            ]);
        });
    });

    describe("#paintMapUptake", function () {
        let result;
        before(async function () {
            result = await mapRequestApi.paintMapUptake();
            if (!result) {
                this.skip(); // <= skips entire describe
            }
        });

        it("response success, returns data as an array", () => {
            expect(result).to.be.an("array");
        });

        it("data array is not empty", () => {
            assert.isNotEmpty(result);
        });

        it("data array is in the correct format", () => {
            assert.containsAllKeys(result[0], [
                "postcodeArea",
                "uptakePercentage",
            ]);
        });
    });

    describe("#getDevicesByPostcodeArea", function () {
        let result;
        before(async function () {
            result = await mapRequestApi.getDevicesByPostcodeArea("AL1");
            if (!result) {
                this.skip(); // <= skips entire describe
            }
        });

        it("response success, returns an array", () => {
            expect(result).to.be.an("array");
        });
    });

    describe("#getUncommissionedDevicesByPostcodeArea", function () {
        let result;
        before(async function () {
            result = await mapRequestApi.getUncommissionedDevicesByPostcodeArea(
                "AL1"
            );
            if (!result) {
                this.skip(); // <= skips entire describe
            }
        });

        it("response success, returns an array", () => {
            expect(result).to.be.an("array");
        });
    });

    describe("#getDevicesByMpxn", function () {
        let result;
        before(async function () {
            result = await mapRequestApi.getDevicesByMpxn();
            if (!result) {
                this.skip(); // <= skips entire describe
            }
        });

        it("response success, returns an array", () => {
            expect(result).to.be.an("array");
        });
    });

    describe("#getLatLng", function () {
        let result;
        before(async function () {
            result = await mapRequestApi.getLatLng(
                "133-135 Victoria St, St Albans,AL1 3XS"
            );
            if (!result) {
                this.skip(); // <= skips entire describe
            }
        });

        it("response success, returns data as an object", () => {
            expect(result).to.be.an("object");
        });

        it("returns results of search", () => {
            assert.notEqual(result.status, "ZERO_RESULTS");
        });

        it("data are in the correct format", () => {
            assert.containsAllKeys(result.results[0], ["geometry"]);
        });
    });
});

// test dashboardRequestApi
describe("dashboardRequestApi", function () {
    describe("#getTotalAmount", function () {
        let result;
        before(async function () {
            result = await dashboardRequestApi.getTotalAmount();
            if (!result) {
                this.skip(); // <= skips entire describe
            }
        });

        it("response success, returns a number", () => {
            expect(result).to.be.a("number");
        });
    });

    describe("#getTotalUncommissionedAmount", function () {
        let result;
        before(async function () {
            result = await dashboardRequestApi.getTotalUncommissionedAmount();
            if (!result) {
                this.skip(); // <= skips entire describe
            }
        });

        it("response success, returns a number", () => {
            expect(result).to.be.a("number");
        });
    });

    describe("#getLowestUptakeArea", function () {
        let result;
        before(async function () {
            result = await dashboardRequestApi.getLowestUptakeArea();
            if (!result) {
                this.skip(); // <= skips entire describe
            }
        });

        it("response success, returns an string", () => {
            expect(result).to.be.a("string");
        });
    });

    describe("#getAcceptableUptakePercentage", function () {
        let result;
        before(async function () {
            result = await dashboardRequestApi.getAcceptableUptakePercentage();
            if (!result) {
                this.skip(); // <= skips entire describe
            }
        });

        it("response success, returns a number", () => {
            expect(result).to.be.a("number");
        });
    });

    describe("#getAverageUptakePercentage", function () {
        let result;
        before(async function () {
            result = await dashboardRequestApi.getAverageUptakePercentage();
            if (!result) {
                this.skip(); // <= skips entire describe
            }
        });

        it("response success, returns a number", () => {
            expect(result).to.be.a("number");
        });
    });
});

mocha.run();
