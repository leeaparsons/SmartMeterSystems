import URLS from "./URLS.js";
import requestApiClient from "./requestApiClient.js";

const paintingService = requestApiClient(URLS.paintingService);
const meterDataService = requestApiClient(URLS.meterDataService);
const mapsGoogleApiService = axios.create({baseURL: URLS.mapsGoogleApisService});

export const paintMapCommissioned = async () => {
    const response = await paintingService.get(`/paintMap/commissioned`);
    return response.data;
};

export const paintMapUptake = async () => {
    const response = await paintingService.get(`/paintMap/uptake`);
    return response.data;
};

export const getDevicesByPostcodeArea = async (postcodeArea) => {
    const response = await meterDataService.get(`/devices/${postcodeArea}`);
    return response.data;
};

export const getUncommissionedDevicesByPostcodeArea = async (postcodeArea) => {
    const response = await meterDataService.get(`/devices/${postcodeArea}/uncommissioned`);
    return response.data;
};

export const getDevicesByMpxn = async (mpxn) => {
    const response = await meterDataService.get(`/devices/mpxn/${mpxn}`);
    return response.data;
};

export const getLatLng = async (address) => {
    const response = await mapsGoogleApiService.get(`/geocode/json?address=${address}&key=AIzaSyA7UWJPz9JpgqoZhX9diEUeLAqjljSZPc8`);
    return response.data;
};