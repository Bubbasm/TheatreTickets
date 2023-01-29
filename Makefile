# Classes that have testers go in the variable TESTS_SHORT
TESTS_SHORT=Seat Sitting Standing Composite AnnualPass CyclePass CreditCard Purchase Reservation PerformanceSitting PerformanceStanding TheatreTickets Customer Ticket Performance Event
TESTS_INSTR=$(TESTS_SHORT:%=%Test)
TEST_JAVA=$(TESTS_INSTR:%=src/model/tests/%.java)

VIEW_SHORT=LogInSignUp Register ManualSeatSelection AutomaticSeatSelection AreaSelection\
  PaymentSelection SearchEntry CycleEntryDetailed EventEntryDetailed NotificationEntry\
  ReservationEntry AnnualPassPurchase CyclePassPurchase Menu AddEventFirst AddEventSecond\
  AddPerformance EditPerformance AddCycleFirst AddCycleSecond AdditionalSettings\
  Statistics DisableSeatsFirst DisableSeatsSecond ModifyTheatreFirst ModifyTheatreSecond
VIEW_INSTR=$(VIEW_SHORT:%=view%)

main:
	javac -d bin -sourcepath src -cp lib/*:. src/main/Main.java
	java -cp bin:lib/*:. main.Main

compile:
	javac -d bin -sourcepath src -cp lib/*:. $$(find ./src/* | grep .java)

compileTests: 
	javac -d bin -sourcepath src -cp lib/*:. $(TEST_JAVA)

run:
	java -cp bin:lib/*:. model.main.Main

doc:
	javadoc -d doc -author -cp lib/*:. -sourcepath src $$(find ./src/* | grep .java)

testAll: compileTests $(TESTS_INSTR)

$(TESTS_INSTR): %:
	@echo -e "-----Testing $@-----\n"
	@java -cp bin:lib/*:. org.junit.runner.JUnitCore model.tests.$@

$(VIEW_INSTR): %:
	javac -d bin -sourcepath src -cp lib/*:. $$(find ./src/* | grep $(@:view%=%).java)
	MY_CLASS=$$(find bin/* | grep $(@:view%=%).class | sed -e 's/.class//g' -e 's/bin.//g'); java -cp bin $$MY_CLASS

.PHONY: clean

cleanData:
	rm -rf data
	@mkdir data

clean:
	rm -rf doc bin printedTickets
	@mkdir printedTickets

all: cleanData clean compile run
