# .env
PWD=$(CURDIR)

# === build ====================================================================
.PHONY: build
build: ## build image
	docker build -t backend:latest ./backend

# === build ====================================================================
.PHONY: build-front
build-front: ## build image
	docker build -t frontend:latest ./frontend

# === start ====================================================================
.PHONY: start
start: ## starts containers
	docker-compose -f docker-compose.yml up -d

# === stop ====================================================================
.PHONY: stop
stop: ## Stops all container
	docker-compose -f docker-compose.yml stop

# === clean ====================================================================
.PHONY: clean
clean:
	$(shell cd ./backend && mvn clean install && mvn clean package)

# === build ====================================================================
.PHONY: first
first: ## first image
	docker build -t backend:latest ./backend && docker-compose -f docker-compose.yml up -d


# === help =====================================================================
.PHONY: help
help:
	@printf '\n \033[33mProject help - \033[m\n\n'
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' ${PWD}/Makefile | awk \
	  'BEGIN {FS = ":.*?## "}; {printf "\033[37m + \033[34m%-20s\033[37m %s\n", $$1, $$2}'
	@printf '\n\033[m'
.PHONY: h
h: help
