file = LOAD '$input' USING PigStorage('\t');
data = FOREACH file GENERATE $30 AS d_total_min, $2 AS actural_departures, $3 AS actural_arrivals, $10 AS average_gate_departure_delay, $11 AS average_gate_arrival_delay, $12 AS delayed_arrivals;
STORE data INTO '$output';