package com.cps2.energy.web;

import java.util.UUID;

public record NotificationToCreateRepresentation(String title, String message, String type, String priority, UUID userId) {
}
