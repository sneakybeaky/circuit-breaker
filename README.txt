This is a proof of concept of the circuit breaker pattern as described in Release It! by Michael Nygard
(http://pragprog.com/titles/mnee/release-it).

I've tried to make this generic by the use of the command pattern. This code base is java 1.4 compatible
as it is meant to be used in the subs project (and others).

For an example of how the circuit breaker works look at com.ft.circuitbreaker.SimpleCircuitBreakerTest. This
shows how to configure the breaker and its expected behaviour.