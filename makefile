CLASS_DIR = classes
SOURCE_DIR = source
MAIN = kalah.AgentBootstrap
JAVAC = javac -d $(CLASS_DIR) -cp $(CLASS_DIR) -sourcepath $(SOURCE_DIR)

help:
	@echo "Commands: build, clean, run, %.class (e.g. make kalah.Agent.class)"

$(CLASS_DIR):
	mkdir $(CLASS_DIR)

%.class: $(CLASS_DIR)
	$(JAVAC) $(SOURCE_DIR)/$(subst .,/,$(basename $@)).java

# Functions
.PHONY: help build clean init run

build: clean $(MAIN).class

clean:
	rm -rf $(CLASS_DIR)

run: build
	java -cp $(CLASS_DIR) $(MAIN)
