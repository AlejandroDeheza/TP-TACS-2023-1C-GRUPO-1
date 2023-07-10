
export const useSessionCache = (() => {
    let sessionCacheInstance;

    const createSessionCache = () => {

        const getCache = (key) => {
            let timestamp = Date.now();
            let stored = sessionStorage.getItem(key);
            let value = null

            if (!stored) 
                return null;

            let jsn = JSON.parse(stored);
            let limit = parseInt(jsn.limit)

            if ((jsn.limit == 0) || (timestamp < limit)) {                
                value = jsn.value;
            }
                        
            return value
        }

        const putCache = (key, value, maxAge) => {
            if (!key || !value)
                return;

            maxAge = !Number.isInteger(maxAge) || maxAge < 0 ? 0 : maxAge * 1000
            let timestamp = Date.now();
            let limit = maxAge == 0 ? 0 : timestamp + maxAge

            let obj = {
                value : value,        
                timestamp : timestamp,
                limit : limit
            };

            sessionStorage.setItem(key, JSON.stringify(obj));
        }

        const clearCache = () => { sessionStorage.clear() }
        const removeCache = (key) => { sessionStorage.removeItem(key) }

        const testCache = () => {
            return "Esto es un test";
        }

        return [getCache, putCache, clearCache, removeCache, testCache];
    }

    return () => {
        if (!sessionCacheInstance) {
            sessionCacheInstance = createSessionCache()
        }

        return sessionCacheInstance;
    }
})()