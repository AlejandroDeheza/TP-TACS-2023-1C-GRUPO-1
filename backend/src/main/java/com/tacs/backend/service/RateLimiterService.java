package com.tacs.backend.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimiterService {
    private static final Integer DEFAULT_API_RPM = 2;

    private static final Integer DEFAULT_USER_RPM = 2;

    private static ConcurrentHashMap<String, UserRequest> usersRequests = new ConcurrentHashMap<>();

    private static Integer apiRPMRequestCount=0;

    private static long requestApiInitialTime;

    public RateLimiterService() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                initializeApiRequestLimit();
                System.out.printf("\nrequestApiInitialTime: %s\n", requestApiInitialTime);

                usersRequests.forEach((key, value) -> {
                    System.out.println("\ntoken: " + key + "\n");
                    System.out.println("\t time: " + value.getLastRequestStartTime() + "\n");
                    System.out.print("\t request counter: " + value.getRequestCount() );
                });
            }
        };

        timer.schedule(task, 0, 1000*5);
    }

    private static void initializeApiRequestLimit() {
        requestApiInitialTime = System.currentTimeMillis();
        apiRPMRequestCount = 0;
    }

    public void initializeUserRequest(String token) {
        usersRequests.put(token, new UserRequest(System.currentTimeMillis()));
    }

    public boolean allowRequest(String token)  {
        apiRPMRequestCount++;
        return apiRPMRequestCount <= DEFAULT_API_RPM && allowUserRequest(token);
    }

    public boolean allowUserRequest(String token) {
      UserRequest userRequest = usersRequests.get(token);
      userRequest.incrementCounter();
      return userRequest.getRequestCount() <=  DEFAULT_USER_RPM;
    }

    @Getter
    @Setter
    static class UserRequest {
            private long lastRequestStartTime;
            private Integer requestCount;
            public UserRequest(long theLastRequestStartTime) {
                lastRequestStartTime = theLastRequestStartTime;
                requestCount = 0;
                Timer timer = new Timer();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        long time = System.currentTimeMillis();
                        updateLastRequestStartTime(time);
                    }
                };
                timer.schedule(task, 0, 1000*5);
            }
            public void incrementCounter() {
                this.requestCount++;
                System.out.println("\nuserRequest.requestCount"+requestCount);
            }
            public void updateLastRequestStartTime(long time) {
                this.lastRequestStartTime = time;
                this.requestCount = 0;
            }
    }
}
