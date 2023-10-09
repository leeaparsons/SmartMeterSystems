const requestApiClient = (baseURL) => {
    const instance = axios.create({
        baseURL: baseURL,
    });

    instance.interceptors.request.use(
        (config) => {
            const authToken = localStorage.getItem('access_token');
            if (authToken) {
                config.headers['Authorization'] = `Bearer ${authToken}`;
            }
            return config;
        },
        (error) => {
            return Promise.reject(error);
        }
    );

    return instance;
};

export default requestApiClient;