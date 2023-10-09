import URLS from "./URLS.js";
import requestApiClient from "./requestApiClient.js";

const dashboardService = requestApiClient(URLS.paintingService);

export const getTotalAmount = async () => {
    const response = await dashboardService.get(`/paintDashboard/total`);
    return response.data;
};

export const getTotalUncommissionedAmount = async () => {
    const response = await dashboardService.get(`/paintDashboard/totalUncommissioned`);
    return response.data;
};

export const getLowestUptakeArea = async () => {
    const response = await dashboardService.get(`/paintDashboard/lowestUptakeArea`);
    return response.data;
};

export const getAcceptableUptakePercentage = async () => {
    const response = await dashboardService.get(`/paintDashboard/acceptableUptake`);
    return response.data;
};

export const getAverageUptakePercentage = async () => {
    const response = await dashboardService.get(`/paintDashboard/averagePercentageUptake`);
    return response.data;
};