package org.backend.models;

/**
 * Represents a group of fans requesting seats in the basketball arena.
 */
public final class FanGroup {
    private final int groupId;
    private final int fanCount;
    private final SeatCategory category;

    /**
     * Constructor for FanGroup.
     */
    public FanGroup(int groupId, int fanCount, SeatCategory category) {
        if (fanCount <= 0) {
            throw new IllegalArgumentException("Fan count must be positive, got: " + fanCount);
        }

        if (category == null) {
            throw new IllegalArgumentException("Seat category cannot be null");
        }

        this.groupId = groupId;
        this.fanCount = fanCount;
        this.category = category;
    }

    /**
     * Gets the group ID.
     *
     * @return Group identifier
     */
    public int getGroupId() {
        return groupId;
    }

    /**
     * Gets the number of fans in this group and return fan count.
     */
    public int getFanCount() {
        return fanCount;
    }

    /**
     * Gets the desired seat category and return seat category.
     */
    public SeatCategory getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return String.format("Group #%d (%d fans, %s)", groupId, fanCount, category);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FanGroup fanGroup = (FanGroup) o;

        return groupId == fanGroup.groupId;
    }

    @Override
    public int hashCode() {
        return groupId;
    }
}