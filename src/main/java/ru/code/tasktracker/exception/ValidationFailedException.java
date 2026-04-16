package ru.code.tasktracker.exception;

public class ValidationFailedException extends RuntimeException
{
    public ValidationFailedException(String message) { super(message); }
}
