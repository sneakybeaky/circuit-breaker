This is a proof of concept of the circuit breaker pattern as described in Release It! by Michael Nygard
(http://pragprog.com/titles/mnee/release-it).

I've tried to make this generic by the use of the command pattern.

For an example of how the circuit breaker works look at com.ninedemons.circuitbreaker.SimpleCircuitBreakerTest. This
shows how to configure the breaker and its expected behaviour.