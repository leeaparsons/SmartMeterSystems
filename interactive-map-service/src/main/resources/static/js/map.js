import * as requestApi from "./network/mapRequestApi.js";
import { greenIcon, orangeIcon, redIcon } from "./colouredPins.js";
const map = L.map("map", { zoomControl: false });
map.addControl(L.control.zoom({ position: "topleft" })); // re-position zoom
map.setView([54.485, -3.12], 6); // default location/zoom level
let geojson;
let currentArea; // current selected postcode area layer

const markersGroup = L.markerClusterGroup({
    spiderfyOnMaxZoom: false,
    showCoverageOnHover: false,
    zoomToBoundsOnClick: true,
    maxClusterRadius: 40,
});

const geocoder = L.Control.geocoder({
    defaultMarkGeocode: false,
})
    .on("markgeocode", function (e) {
        var bbox = e.geocode.bbox;
        var poly = L.polygon([
            bbox.getSouthEast(),
            bbox.getNorthEast(),
            bbox.getNorthWest(),
            bbox.getSouthWest(),
        ]);
        map.fitBounds(poly.getBounds());
    })
    .addTo(map);

const layerGroup = L.tileLayer(
    "https://tile.openstreetmap.org/{z}/{x}/{y}.png",
    {
        maxZoom: 19,
        minZoom: 2,
        attribution:
            '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
    }
);

const style = {
    weight: 0.6,
    fillOpacity: 0.2,
};

const layerPostalCodes = L.geoJSON(postalCodes, {
    style: style,
    onEachFeature: onEachFeature,
});

const mediaQuery = window.matchMedia("(max-width: 840px)");
const legend = L.control({ position: "bottomright" });
const searchByMpxnForm = document.getElementById("search-form");
const legendControllers = L.control({ position: "bottomright" });
const filterMenu = mediaQuery.matches ? L.control({ position: "bottomright" }) : L.control({ position: "bottomleft" });
const scrollButton = L.control({ position: "bottomleft" });

searchByMpxnForm.addEventListener("submit", onSearchByMpxnFormSubmit);

legend.onAdd = createLegendCommissioned;
legendControllers.onAdd = createLegendControllers;
filterMenu.onAdd = createFilterMenu;
scrollButton.onAdd = createScrollButton;

setMapBounds(map); // restricts map to create multiple worlds

layerGroup.addTo(map);
geojson = layerPostalCodes.addTo(map);
filterMenu.addTo(map);
scrollButton.addTo(map);
legendControllers.addTo(map);
legend.addTo(map);
map.addLayer(markersGroup);

colorPostCodeAreas("commissionedPercentage");

const filterCheckbox = document.getElementById("filter-commissioned-checkbox");
filterCheckbox.addEventListener("change", async (e) => {
    if(currentArea) {
        await onPostCodeAreaClick(currentArea)
    }
})

const paintingMethodRadioButtons = document.querySelectorAll(
    "input[name='paintingMethod']"
);

const scrollButtonController = document.getElementById("scrollButton");
scrollButtonController.addEventListener("click",  (e) => {
    window.scrollBy(0, window.innerHeight);
})

paintingMethodRadioButtons.forEach(paintingMethodButton => {
    paintingMethodButton.addEventListener("change", async (e) => {
        const paintingMethod = e.currentTarget.value;
        await colorPostCodeAreas(paintingMethod);
        if (paintingMethod === "commissionedPercentage") {
            legend.onAdd = createLegendCommissioned;
            legend.addTo(map);
        } else if (paintingMethod === "uptakePercentage") {
            legend.onAdd = createLegendUptake;
            legend.addTo(map);
        }
        if(currentArea) {
            currentArea.bringToFront();
        }
    });
});

function setMapBounds(map) {
    var southWest = L.latLng(-89.98155760646617, -180),
        northEast = L.latLng(89.99346179538875, 180);
    var bounds = L.latLngBounds(southWest, northEast);

    map.setMaxBounds(bounds);
    map.on('drag', function() {
        map.panInsideBounds(bounds, { animate: false });
    });
}

function createScrollButton() {
    const button = L.DomUtil.create("button", "scroll-button");
    button.setAttribute('type', 'button');
    button.setAttribute('id', 'scrollButton');
    button.setAttribute('aria-label', 'Scroll down button');
    button.innerHTML = `
        <img
           src=${"/images/arrow-button.svg"}
           alt="status"
           width="50"
           height="50"
        />`;
    return button;
}

function createLegendControllers() {
    const div = L.DomUtil.create("div", "info legend");
    div.innerHTML = `
        <h4>Color map by:</h4>
        <input
        type="radio"
        id="commissionedPercentage"
        name="paintingMethod"
        value="commissionedPercentage"
        checked
        />
        <label for="commissionedPercentage">commissioned %</label><br/>
        <input
        type="radio"
        id="uptakePercentage"
        name="paintingMethod"
        value="uptakePercentage"
        />
        <label for="uptakePercentage">uptake</label>`;
    return div;
}

function createFilterMenu() {
    const container = L.DomUtil.create("div", "filter-menu-container");
    const commissionCheckboxLabel = document.createElement("label");
    commissionCheckboxLabel.htmlFor = "filter-commissioned-checkbox";
    commissionCheckboxLabel.textContent = "Uncommissioned";
    const commissionCheckbox = document.createElement("input");
    commissionCheckbox.type = "checkbox";
    commissionCheckbox.id = "filter-commissioned-checkbox";
    container.appendChild(commissionCheckbox);
    container.appendChild(commissionCheckboxLabel);
    return container;
}

function createLegendCommissioned() {
    const div = L.DomUtil.create("div", "info legend"),
        grades = [1, 0.75, 0.5, 0.25, 0],
        labels = ["All", "> 75%", "> 50%", "> 25%", "< 25%"];

    div.innerHTML = "<h4>Smart Meters<br>commisioned</h4>";
    colorLegend(div, grades, labels, getColorCommissioned);
    return div;
}

function createLegendUptake() {
    const div = L.DomUtil.create("div", "info legend"),
        grades = [0.8, 0.6, 0.4, 0.2, 0],
        labels = ["> 80%", "> 60%", "> 40%", "> 20%", "< 20%"];

    div.innerHTML = "<h4>Smart Meters<br>uptake</h4>";
    colorLegend(div, grades, labels, getColorUptake);
    return div;
}

function colorLegend(container, grades, labels, getColor) {
    for (let i = 0; i < grades.length; i++) {
        container.innerHTML += `<i style="background: ${getColor(
            grades[i]
        )}"></i> ${labels[i] ? labels[i] + "<br>" : labels[i]}`;
    }
}

function getColorUptake(value) {
    if (value >= 0.8) {
        return "#552BBB";
    } else if (value >= 0.6 && value < 0.8) {
        return "#1F60C2";
    } else if (value >= 0.4 && value < 0.6) {
        return "#2F7BB1";
    } else if (value >= 0.2 && value < 0.4) {
        return "#31B0C1";
    } else {
        return "#6CC5BF";
    }
}

function getColorCommissioned(value) {
    if (value === 1) {
        return "#7AD152";
    } else if (value >= 0.75 && value < 1) {
        return "#31B0C1";
    } else if (value >= 0.5 && value < 0.75) {
        return "#3E77B5";
    } else if (value >= 0.25 && value < 0.5) {
        return "#552C89";
    } else {
        return "#A4286E";
    }
}

function zoomToFeature(layer) {
    map.fitBounds(layer.getBounds());
}

function onEachFeature(feature, layer) {
    layer.on({
        click: escapeZoom,
    });
}

function escapeZoom(e) {
    const latlng = e.latlng;
    markersGroup.clearLayers();
    map.setView([latlng.lat, latlng.lng], 10);
    if (currentArea) {
        setLayerStyle(currentArea, {
            weight: 0.6,
            color: "#3388ff",
            fillColor: currentArea.options.fillColor,
        });
        const container = document.querySelector(".meter-info");
        container.innerHTML = `<p class="message">
            Select the area to display information <br />
            about Smart Meters
        </p>`;
    }
}

function setLayerStyle(layer, style) {
    layer.setStyle(style);
    layer.bringToFront();
}

async function colorPostCodeAreas(paintingMethod) {
    try {
        let postcodeAreas = null;
        if (paintingMethod === "commissionedPercentage") {
            postcodeAreas = await requestApi.paintMapCommissioned();
        } else if (paintingMethod === "uptakePercentage") {
            postcodeAreas = await requestApi.paintMapUptake();
        }

        if (!postcodeAreas) {
            return;
        }

        let currentAreaSelected;

        const layers = layerPostalCodes.getLayers();
        postcodeAreas.forEach(area => {
            const meterLayer = layers.find(
                layer => layer.feature.properties.name === area.postcodeArea
            );

            let color;

            if (meterLayer) {
                if (paintingMethod === "commissionedPercentage") {
                    const commissioned = area.commissionedPercentage;
                    color = getColorCommissioned(commissioned);
                } else if (paintingMethod === "uptakePercentage") {
                    const uptake = area.uptakePercentage;
                    color = getColorUptake(uptake);
                }

                setLayerStyle(meterLayer, {
                    fillColor: color,
                    fillOpacity: 0.7,
                });

                meterLayer.off({ click: escapeZoom });

                if (meterLayer._events.click.length === 0) {
                    meterLayer.on({
                        click: e => onPostCodeAreaClick(e.target),
                    });
                }

                if (meterLayer === currentArea) {
                    currentAreaSelected = meterLayer;
                    setLayerStyle(currentArea, {
                        weight: 5,
                        color: color,
                        fillOpacity: 0.7,
                    });
                }
            }
        });
    } catch (error) {
        console.log(error);
    }
}

async function onPostCodeAreaClick(layer) {
    zoomToFeature(layer);
    markersGroup.clearLayers();

    if (currentArea) {
        setLayerStyle(currentArea, {
            weight: 0.5,
            color: "#3388ff",
            fillColor: currentArea.options.fillColor,
        });
    }

    setLayerStyle(layer, {
        weight: 4,
        color: layer.options.fillColor,
        fillOpacity: 0.7,
    });

    currentArea = layer;

    await displayMeterData(currentArea);
}

async function displayMeterData(currentArea) {
    const postcodeArea = currentArea.feature.properties.name;
    const filter = document.getElementById("filter-commissioned-checkbox"); // variable, that contains the state of filter (checked/unchecked)
    let meterData;
    try {
        if (filter.checked) {
            meterData = await requestApi.getUncommissionedDevicesByPostcodeArea(postcodeArea);
        } else {
            meterData = await requestApi.getDevicesByPostcodeArea(postcodeArea);
        }

        meterData.forEach(createMarker);
        showMeterInfoByPostcodeAreaMarkup(postcodeArea, meterData);
    } catch (error) {
        console.log(error);
    }
}

// Methods to add a marker to the map
async function createMarker(mpxn) {
    try {
        const devices = mpxn.devices;
        const commissionedFlavour = getCommissionedPercentagePoint(devices);
        let point = await getLatLng(mpxn);
        addMarkerToMap(mpxn, point[0], point[1], commissionedFlavour);
    } catch (error) {
        console.log(error);
    }
}

//Returns the average commissioned percentage a single point!
function getCommissionedPercentagePoint(devices) {
    let commissioned = 0;
    let notCommissioned = 0;

    devices.forEach(device => {
        if (device.deviceStatus === "COMMISSIONED") {
            commissioned += 1;
        } else {
            notCommissioned += 1;
        }
    });

    const avg = commissioned / (commissioned + notCommissioned);

    if (avg === 1) {
        return "good";
    } else if (avg > 0.25) {
        return "okay";
    } else {
        return "bad";
    }
}

//Method for getting the Lat/Lng coords for a device
async function getLatLng(mpxn) {
    try {
        const address = mpxn.address + "," + mpxn.postcode;

        const jsonData = await requestApi.getLatLng(address);

        let testMarker = jsonData.results[0].geometry.location;
        let lat = testMarker.lat;
        let lng = testMarker.lng;
        return [lat, lng];
    } catch (error) {
        console.log(error);
    }
}

//Places the marker on the map
function addMarkerToMap(mpxn, lat, lng, commissionedFlavour) {
    switch (commissionedFlavour) {
        case "bad":
            const markerBad = L.marker([lat, lng], { icon: redIcon }).on(
                "click",
                () => showMeterInfoByMpxnMarkup(mpxn)
            );
            markerBad.addTo(markersGroup);
            break;
        case "good":
            const markerGood = L.marker([lat, lng], { icon: greenIcon }).on(
                "click",
                () => showMeterInfoByMpxnMarkup(mpxn)
            );
            markerGood.addTo(markersGroup);
            break;
        case "okay":
            const markerOkay = L.marker([lat, lng], { icon: orangeIcon }).on(
                "click",
                () => showMeterInfoByMpxnMarkup(mpxn)
            );
            markerOkay.addTo(markersGroup);
            break;
    }
}

async function onSearchByMpxnFormSubmit(e) {
    e.preventDefault();
    const form = e.currentTarget;
    const searchQuery = form.elements.query.value;
    if (!searchQuery) {
        return;
    }

    try {
        const meterData = await requestApi.getDevicesByMpxn(searchQuery);
        createMarker(meterData[0]);
        showMeterInfoByMpxnMarkup(meterData[0]);

        const layers = layerPostalCodes.getLayers();
        const meterLayer = layers.find(
            layer =>
                layer.feature.properties.name ===
                meterData[0].postcode.split(" ")[0]
        );

        zoomToFeature(meterLayer);
        markersGroup.clearLayers();

        if (currentArea) {
            setLayerStyle(currentArea, {
                weight: 0.6,
                color: "#3388ff",
                fillColor: currentArea.options.fillColor,
            });
        }

        setLayerStyle(meterLayer, {
            weight: 4,
            color: meterLayer.options.fillColor,
            fillOpacity: 0.7,
        });

        currentArea = meterLayer;
    } catch (error) {
        console.log(error);
        const container = document.querySelector(".meter-info");
        container.innerHTML = `<p class="message">
            No Smart Meters were found with MPXN: ${searchQuery}
        </p>`;
    }
}

function showMeterInfoByMpxnMarkup(mpxn) {
    const container = document.querySelector(".meter-info");
    if (mpxn.devices?.length > 0) {
        container.innerHTML = "";
        const meterContainerMarkup = createListMarkup(mpxn.devices, createMeterInfoContainerMarkup);
        const markup = `
        <div>
            <div class="meter-info_title">MPXN: ${mpxn.mpxn}</div>
            <div class="meter-info_address">${mpxn.address}, ${mpxn.postcode}</div>
            ${meterContainerMarkup}
            <button type="button" class="meter-info_button" id="meter-info-button">Show postcode area</button>
        </div>`;
        container.insertAdjacentHTML("beforeend", markup);
        const backButton = document.querySelector("#meter-info-button");
        backButton.addEventListener("click", async (e) => {
            await onPostCodeAreaClick(currentArea);
        })
    } else {
        container.innerHTML = `<p class="message">
            Select the area to display information <br />
            about Smart Meters
        </p>`;
    }
}


function showMeterInfoByPostcodeAreaMarkup(postcodeArea, mpxnData) {
    const container = document.querySelector(".meter-info");
    if (mpxnData?.length > 0) {
        container.innerHTML = "";
        const meterContainerMarkup = createListMarkup(mpxnData, createMpxnInfoContainerMarkup);
        const markup = `
        <div>
            <div class="meter-info_title">Postcode area: ${postcodeArea}</div>
            <div class="meter-info_address">Amount of MPXNs: ${mpxnData.length}</div>
            ${meterContainerMarkup}
        </div>`;
        container.insertAdjacentHTML("beforeend", markup);
        const meterList = document.querySelector(".meter_list");
        meterList.addEventListener("click", async (e) => {
            if(e.target.id === "mpxn-button") {
                await showMeterInfoByMpxnMarkup(mpxnData[e.target.dataset.id]);
            }
        })
    } else {
        container.innerHTML = `<p class="message">
            Select the area to display information <br />
            about Smart Meters
        </p>`;
    }
}

function createListMarkup(itemsList, createContainerMarkup) {
    const meterItems = itemsList.map((item, index) => createContainerMarkup(item, index)).join("");
    return `<ul class="meter_list">
        ${meterItems}
    </ul>`;
}

function createMeterInfoContainerMarkup(smartMeter) {
    const meterTypes = {
        CHF: "Communications Hub Function",
        ESME: "Electricity Smart Metering Equipment",
        GPF: "Gas Proxy Function",
        GSME: "Gas Smart Metering Equipment",
        PPMID: "Pre-Payment Interface Device",
    };
    const {
        deviceType,
        deviceId,
        deviceStatus,
        deviceModel,
        deviceManufacturer,
        deviceFirmwareVersion,
        deviceFirmwareVersionStatus,
    } = smartMeter;
    const meterKey = Object.keys(meterTypes).find(
        key => key.toLowerCase() === deviceType.toLowerCase()
    );
    const meterTypeName = meterKey ? meterTypes[meterKey] : deviceType;
    return `
    <li class="meter_container">
        <div class="meter-type">
            <h2 class="meter-type_header">${meterTypeName}</h2>
        </div>
        <ul class="meter-data">
            <li class="meter-data_item">
                <span class="meter-data_title">Id: </span>
                <span class="meter-data_value">${deviceId}</span>
            </li>
            <li class="meter-data_item">
                <span class="meter-data_title">Status: </span>
                <span class=${
        deviceStatus === "COMMISSIONED"
            ? "meter-data_value--active"
            : "meter-data_value--not-active"
    }>${deviceStatus}</span>
                <img
                src=${
        deviceStatus === "COMMISSIONED"
            ? "/images/active.svg"
            : "/images/not-active.svg"
    }
                alt="status"
                width="15"
                />
            </li>
            <li class="meter-data_item">
                <span class="meter-data_title">Model: </span>
                <span class="meter-data_value">${deviceModel}</span>
            </li>
            <li class="meter-data_item">
                <span class="meter-data_title">Manufacturer: </span>
                <span class="meter-data_value">${deviceManufacturer}</span>
            </li>
            <li class="meter-data_item">
                <span class="meter-data_title">Firmware Version: </span>
                <span class="meter-data_value">${deviceFirmwareVersion}</span>
            </li>
            <li class="meter-data_item">
                <span class="meter-data_title">Firmware Status: </span>
                <span class=${
        deviceFirmwareVersionStatus === "ACTIVE"
            ? "meter-data_value--active"
            : "meter-data_value--not-active"
    }>${deviceFirmwareVersionStatus}</span>
                <img
                src=${
        deviceFirmwareVersionStatus === "ACTIVE"
            ? "/images/active.svg"
            : "/images/not-active.svg"
    }
                alt="status"
                width="15"
                />
            </li>
        </ul>
    </li>`;
}

function createMpxnInfoContainerMarkup(mpxnData, index) {
    const { mpxn, address, postcode, devices } = mpxnData;
    return `
    <li class="mpxn_container">
        <ul class="mpxn-data">
            <li class="mpxn-data_item">
                <span class="mpxn_header">MPXN: ${mpxn}</span>
            </li>
            <li class="mpxn-data_item">
                <span class="mpxn-data_title">Amount of devices: </span>
                <span class="mpxn-data_value">${devices.length}</span>
            </li>
            <li class="mpxn-data_item">
                <span class="mpxn-data_title">Postcode: </span>
                <span class="mpxn-data_value">${postcode}</span>
            </li>
        </ul>
        <button type="button" class="mpxn_button" id="mpxn-button" data-id="${index}">Devices</button>
    </li>`;
}