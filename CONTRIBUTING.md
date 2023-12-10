# Contributing to Social Media API Backend

Thank you for considering contributing to the Social Media API Backend project! This document outlines the process for contributing to the project.

## Code of Conduct

By participating in this project, you agree to abide by our Code of Conduct.

## How Can I Contribute?

### Reporting Bugs

- Check if the bug has already been reported in the Issues section
- Use the bug report template to create a new issue
- Include detailed steps to reproduce the bug
- Include system information (OS, Java version, etc.)

### Suggesting Features

- Check if the feature has already been suggested in the Issues section
- Use the feature request template to create a new issue
- Describe the feature in detail, including potential implementation approaches

### Pull Requests

1. Fork the repository
2. Create a new branch (`git checkout -b feature/amazing-feature`)
3. Make your changes
4. Run tests to ensure they pass
5. Commit your changes (`git commit -m 'Add some amazing feature'`)
6. Push to the branch (`git push origin feature/amazing-feature`)
7. Open a Pull Request

## Development Workflow

### Setting Up Development Environment

1. Install prerequisites (Java 17+, Maven, Docker)
2. Clone the repository
3. Set up the database (using Docker Compose or local PostgreSQL)
4. Run the application

### Code Style

This project follows the Google Java Style Guide. Please ensure your code conforms to this style guide.

### Testing

- Write unit tests for all new code
- Write integration tests for API endpoints
- Ensure all tests pass before submitting a PR

### Commit Messages

Please use clear and descriptive commit messages. Follow the conventional commits specification:

```
type(scope): description

[optional body]

[optional footer]
```

Types: feat, fix, docs, style, refactor, test, chore

## Reviewing Process

- All PRs require at least one review before merging
- Address all comments and requested changes
- Maintainers will merge approved PRs

Thank you for your contributions!
