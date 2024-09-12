all: help

.PHONY: init
init: ## Initialize the project
	$(MAKE) clean
	$(MAKE) build
	$(MAKE) lint-detekt
	$(MAKE) lint-ktlint
	$(MAKE) format

.PHONY: clean
clean: ## Clean the environment
	@echo "👉 Cleaning the project..."
	@./gradlew clean

.PHONY: build
build: ## Build the project
	@echo "👉 Building the project..."
	@./gradlew clean build

.PHONY: format
format: ## Format the code using Ktlint
	@echo "👉 Formatting the code using Ktlint..."
	@./gradlew ktlintFormat

.PHONY: lint
lint: ## Run linting
	@echo "Please specify which lint you would like to run:"
	@echo "1. Detekt"
	@echo "2. Ktlint"
	@read -p "Enter choice [1-2]: " choice; \
	if [ $$choice -eq 1 ]; then \
		$(MAKE) lint-detekt; \
	elif [ $$choice -eq 2 ]; then \
		$(MAKE) lint-ktlint; \
	else \
		echo "Invalid choice"; \
	fi

.PHONY: lint-detekt
lint-detekt: ## Run Detekt linting
	@echo "👉 Running Detekt linting..."
	@./gradlew detekt

.PHONY: lint-ktlint
lint-ktlint: ## Run Ktlint linting
	@echo "👉 Running Ktlint linting..."
	@./gradlew ktlintCheck

.PHONY: apk-debug
apk-debug: ## Build apk file in debug mode
	@echo "👉 Building apk file in debug mode..."
	@./gradlew assembleDebug

.PHONY: apk-release
apk-release: ## Build apk file in release mode
	@echo "👉 Building apk file in release mode..."
	@./gradlew assembleRelease

.PHONY: help
help: ## Show help message
	@echo "⚡️ List of available commands:"
	@egrep '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-20s\033[0m %s\n", $$1, $$2}'