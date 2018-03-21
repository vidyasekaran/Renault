
-- 04-11-2015
INSERT INTO "CP200135_USR01"."MST_PRMTR" 
(SEQ_NO, PRMTR_CD, KEY1,KEY2, VAL1, VAL2) 
 VALUES ('001', 'mnthlyVolOcfLmtAllocPatnFilters', '07', ' ', 'Copy M/S to Alloc (Total and Buyer Group)', '1');
 
 INSERT INTO "CP200135_USR01"."MST_PRMTR" 
(SEQ_NO, PRMTR_CD, KEY1,KEY2, VAL1, VAL2) 
 VALUES ('002', 'mnthlyVolOcfLmtAllocPatnFilters', '07', ' ', 'Copy M/S to Alloc  (Buyer Group only)', '2');

 INSERT INTO "CP200135_USR01"."MST_PRMTR" 
(SEQ_NO, PRMTR_CD, KEY1,KEY2, VAL1, VAL2) 
 VALUES ('003', 'mnthlyVolOcfLmtAllocPatnFilters', '07', ' ', 'Copy Order to Alloc (Total and Buyer Group)', '3');

 INSERT INTO "CP200135_USR01"."MST_PRMTR" 
(SEQ_NO, PRMTR_CD, KEY1,KEY2, VAL1, VAL2) 
 VALUES ('004', 'mnthlyVolOcfLmtAllocPatnFilters', '07', ' ', 'Copy Order to Alloc (Buyer Group only)', '4');

  INSERT INTO "CP200135_USR01"."MST_PRMTR" 
(SEQ_NO, PRMTR_CD, KEY1,KEY2, VAL1, VAL2) 
 VALUES ('005', 'mnthlyVolOcfLmtAllocPatnFilters', '07', ' ', 'Auto Alloc by M/S ratio ', '5');

  INSERT INTO "CP200135_USR01"."MST_PRMTR" 
(SEQ_NO, PRMTR_CD, KEY1,KEY2, VAL1, VAL2) 
 VALUES ('006', 'mnthlyVolOcfLmtAllocPatnFilters', '07', ' ', 'Auto Alloc by Order ratio', '6');

  INSERT INTO "CP200135_USR01"."MST_PRMTR" 
(SEQ_NO, PRMTR_CD, KEY1,KEY2, VAL1, VAL2) 
 VALUES ('007', 'mnthlyVolOcfLmtAllocPatnFilters', '07', ' ', 'Allocation - without Cumulative', '7');

  INSERT INTO "CP200135_USR01"."MST_PRMTR" 
(SEQ_NO, PRMTR_CD, KEY1,KEY2, VAL1, VAL2) 
 VALUES ('008', 'mnthlyVolOcfLmtAllocPatnFilters', '07', ' ', 'Allocation - with Cumulative without Extra MS OCF', '8');

 
  INSERT INTO "CP200135_USR01"."MST_PRMTR" 
(SEQ_NO, PRMTR_CD, KEY1,KEY2, VAL1, VAL2) 
 VALUES ('009', 'mnthlyVolOcfLmtAllocPatnFilters', '07', ' ', 'Allocation - with Cumulative & Extra MS OCF ', '9');
 
 
 
 
 INSERT INTO "CP200135_USR01"."MST_PRMTR" 
(SEQ_NO, PRMTR_CD, KEY1,KEY2, VAL1, VAL2) 
 VALUES ('001', 'mnthlyVolOcfLmtAllocVolOROcfFilters', '07', ' ', 'VOLUME ONLY', '1');
 
 INSERT INTO "CP200135_USR01"."MST_PRMTR" 
(SEQ_NO, PRMTR_CD, KEY1,KEY2, VAL1, VAL2) 
 VALUES ('002', 'mnthlyVolOcfLmtAllocVolOROcfFilters', '07', ' ', 'OCF ONLY ', '2');

 INSERT INTO "CP200135_USR01"."MST_PRMTR" 
(SEQ_NO, PRMTR_CD, KEY1,KEY2, VAL1, VAL2) 
 VALUES ('003', 'mnthlyVolOcfLmtAllocVolOROcfFilters', '07', ' ', 'ALL', '3');

 


-- delete from "CP200135_USR01"."MST_PRMTR"  where PRMTR_CD = 'mnthlyVolOcfLmtAllocHrznFilters';
INSERT INTO "CP200135_USR01"."MST_PRMTR" 
(SEQ_NO, PRMTR_CD, KEY1,KEY2, VAL1, VAL2) 
 VALUES ('001', 'mnthlyVolOcfLmtAllocHrznFilters', '07', ' ', 'Full', 'FULL');
 
 INSERT INTO "CP200135_USR01"."MST_PRMTR" 
(SEQ_NO, PRMTR_CD, KEY1,KEY2, VAL1, VAL2) 
 VALUES ('002', 'mnthlyVolOcfLmtAllocHrznFilters', '07', ' ', '3 Month（N-N+2）', 'N-N+2');

 INSERT INTO "CP200135_USR01"."MST_PRMTR" 
(SEQ_NO, PRMTR_CD, KEY1,KEY2, VAL1, VAL2) 
 VALUES ('003', 'mnthlyVolOcfLmtAllocHrznFilters', '07', ' ', 'N Month', 'N');

 INSERT INTO "CP200135_USR01"."MST_PRMTR" 
(SEQ_NO, PRMTR_CD, KEY1,KEY2, VAL1, VAL2) 
 VALUES ('004', 'mnthlyVolOcfLmtAllocHrznFilters', '07', ' ', 'N+1 Month', 'N+1');

  INSERT INTO "CP200135_USR01"."MST_PRMTR" 
(SEQ_NO, PRMTR_CD, KEY1,KEY2, VAL1, VAL2) 
 VALUES ('005', 'mnthlyVolOcfLmtAllocHrznFilters', '07', ' ', 'N+2 Month', 'N+2');

  INSERT INTO "CP200135_USR01"."MST_PRMTR" 
(SEQ_NO, PRMTR_CD, KEY1,KEY2, VAL1, VAL2) 
 VALUES ('006', 'mnthlyVolOcfLmtAllocHrznFilters', '07', ' ', 'N+3 Onwards', 'N+3_Onwords');
 
 
INSERT INTO "MST_PRMTR" (SEQ_NO, PRMTR_CD, KEY1, KEY2, VAL1, VAL2) VALUES ('1', 'CHANGE RESULT', '01', ' ', 'SUCCESS', 'P');
INSERT INTO "MST_PRMTR" (SEQ_NO, PRMTR_CD, KEY1, KEY2, VAL1, VAL2) VALUES ('2', 'CHANGE RESULT', '02', ' ', 'FAILURE', 'F');

