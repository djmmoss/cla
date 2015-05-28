
SBT ?= sbt
SBT_FLAGS ?= -Dsbt.log.noformat=true
# If a chiselVersion is defined, use that.
# Otherwise, if we're making either "smoke" or "check" use the snapshot.
# Otherwise, use the latest release.
ifneq (,$(chiselVersion))
	SBT_FLAGS += -DchiselVersion="$(chiselVersion)"
else
	ifneq (,$(filter smoke check,$(MAKECMDGOALS)))
		SBT_FLAGS += -DchiselVersion="2.3-SNAPSHOT"
	else
		SBT_FLAGS += -DchiselVersion="latest.release"
	endif
endif

CHISEL_FLAGS :=

top_srcdir ?= .
srcdir ?= src/main/scala/*
executables := $(filter-out top, $(notdir $(basename $(wildcard $(srcdir)/*.scala))))
outs := $(addsuffix .out, $(executables))

default: emulator

all: emulator verilog # dreamer

clean:
	-rm -f *.h *.hex *.flo *.cpp *.o *.out *.v *.vcd $(executables)
	-rm -rf project/target/ target/

emulator: $(outs)

dreamer: $(addsuffix .hex, $(executables))

verilog: $(addsuffix .v, $(executables))

# We need to set the shell options -e -o pipefail here or the exit
# code will be the exit code of the last element of the pipeline - the tee.
# We should be able to do this with .POSIX: or .SHELLFLAGS but they don't
# appear to be support by Make 3.81

%.out: $(srcdir)/%.scala
	set -e pipefail; $(SBT) $(SBT_FLAGS) "run $(notdir $(basename $<)) --genHarness --compile --test --backend c --vcd $(CHISEL_FLAGS)" | tee $@

%.hex: $(srcdir)/%.scala
	$(SBT) $(SBT_FLAGS) "run $(notdir $(basename $<)) --backend flo --genHarness --compile --test $(CHISEL_FLAGS)"

%.v: $(srcdir)/%.scala
	$(SBT) $(SBT_FLAGS) "run $(notdir $(basename $<)) --genHarness --backend v $(CHISEL_FLAGS)"

smoke:
	$(SBT) $(SBT_FLAGS) compile

.PHONY: all check clean emulator verilog smoke
