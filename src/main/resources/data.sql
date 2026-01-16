INSERT INTO customer(name) VALUES ('박나경');

INSERT INTO movie(title, fee) VALUES ('한산', 10000);

INSERT INTO discount_policy(movie_id, policy_type, amount, percent) VALUES (1, 'AMOUNT', 1000, NULL);
INSERT INTO discount_policy(movie_id, policy_type, amount, percent) VALUES (1, 'PERCENT', NULL, 0.1);

INSERT INTO discount_condition(policy_id, condition_type, sequence, day_of_week, start_time, end_time) VALUES (1, 'SEQUENCE', 1, NULL, NULL, NULL);
INSERT INTO discount_condition(policy_id, condition_type, sequence, day_of_week, start_time, end_time) VALUES (1, 'PERIOD', NULL, 'WEDNESDAY', '09:00', '11:30');
INSERT INTO discount_condition(policy_id, condition_type, sequence, day_of_week, start_time, end_time) VALUES (2, 'SEQUENCE', 1, NULL, NULL, NULL);
INSERT INTO discount_condition(policy_id, condition_type, sequence, day_of_week, start_time, end_time) VALUES (2, 'PERIOD', NULL, 'WEDNESDAY', '09:00', '11:30');

INSERT INTO screening(movie_id, sequence, when_screened) VALUES (1, 1, '2024-11-08 09:00');
