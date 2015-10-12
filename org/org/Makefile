# Makefile for most Java-based RoSuDa projects
# $Id: Makefile 3487 2013-11-16 15:57:23Z helbig $
#
# Note that some projects may be better compiled using xcodebuild
# Requires GNU make (or compatible)!

JAPIURL:=http://java.sun.com/j2se/1.4.2/docs/api
ifeq ($(JDKVER),)
JDKVER:=1.4
endif
JFLAGS+=-encoding UTF-8 -target $(JDKVER) -source $(JDKVER)

# determine host OS, we need a different path separator for Windows
IS_WIN32=$(shell if echo "${OS}"|grep -i windows >/dev/null 2>&1; then echo yes; else echo no; fi)
ifeq ($(IS_WIN32),yes)
PATHSEP=\;
else
PATHSEP=:
endif

# set the default (direct) Java/R engine for all projects that need one
JRENGINE=JRI.jar

#----------- source definitions --------------

DEDUCER_SRC:= $(wildcard rosuda/deducer/*.java) $(wildcard rosuda/deducer/widgets/*.java) $(wildcard rosuda/deducer/widgets/event/*.java) $(wildcard rosuda/deducer/widgets/param/*.java) $(wildcard rosuda/deducer/models/*.java) $(wildcard rosuda/deducer/menu/*.java) $(wildcard rosuda/deducer/menu/twosample/*.java)  $(wildcard rosuda/deducer/toolkit/*.java) $(wildcard rosuda/deducer/data/*.java) $(wildcard rosuda/deducer/plots/*.java)

#--------- targets ---------

TARGETS=educer.jar

JAVAC=javac $(JFLAGS)

all: $(TARGETS)


deducer.jar: $(DEDUCER_SRC) 
	rm -rf org
	$(JAVAC) -d . -classpath JGR.jar$(PATHSEP)MRJstubs.jar$(PATHSEP)iplots.jar$(PATHSEP)javaGD.jar$(PATHSEP)$(JRENGINE)  $(DEDUCER_SRC)
	cp -rf rosuda/projects/deducer/icons .
	cp -rf rosuda/projects/deducer/templates .
	jar fcm $@ rosuda/projects/deducer/deducer.mft org icons templates
	rm -rf org icons templates

.PHONY: clean all doc docs
