standard_report_file = LOAD '$input1' USING PigStorage(',');
standard_report = FOREACH standard_report_file GENERATE $0 AS facility, $1 AS date, $2 AS actural_departures, $3 AS actural_arrivals, $4 AS departure_cancellations, $5 AS arrival_cancellations, $6 AS departure_diversions, $7 AS arrival_diversions, $9 AS ontime_gate_departures, $10 AS ontime_gate_arrivals, $11 AS average_gate_departure_delay, $12 AS average_gate_arrival_delay, $16 AS delayed_arrivals, $17 AS average_delay_per_delayed_arrival;
filter_standard_report = FILTER standard_report BY INDEXOF(facility, "Sub-Total for")!= -1;

causal_report_file = LOAD '$input2' USING PigStorage(',');
causal_report = FOREACH causal_report_file GENERATE $0 AS facility, $1 AS date, $5 AS c_carrier, $6 AS c_weather, $7 AS c_nas, $8 AS c_security, $10 AS d_carrier_min, $11 AS d_carrier_flt, $12 AS d_weather_min, $13 AS d_weather_flt, $14 AS d_nas_min, $15 AS d_nas_flt, $16 AS d_security_min, $17 AS d_security_flt, $18 AS d_late_arrival_min, $19 AS d_late_arrival_flt, $20 AS d_total_min, $21 AS d_total_flt;
filter_causal_report = FILTER causal_report BY INDEXOF(facility, "Sub-Total for")!= -1;

data = JOIN filter_standard_report BY (facility, date) FULL OUTER, causal_report_file BY (facility, date);
STORE data INTO '$output';