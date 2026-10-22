package com.rewards.dto;

/**
 * Represents the total reward points earned by a customer in a single month.
 *
 * @param month  the month name (e.g. "2026-01")
 * @param points total points earned in that month
 */
public record MonthlyPoints(String yearMonth, int points) {}
