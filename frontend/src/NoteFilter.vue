<template>
  <div class="filter-container">
    <img src="./assets/images/filter.svg" alt="Filter" class="filter-icon" />
    <button
      :class="['filter-btn', { active: selectedFilter === 'all' }]"
      @click="selectFilter('all')"
    >
      All
    </button>
    <button
      :class="[
        'filter-btn',
        'unimportant',
        { active: selectedFilter === 'unimportant' },
      ]"
      @click="selectFilter('unimportant')"
    >
      Unimportant
    </button>
    <button
      :class="[
        'filter-btn',
        'serious',
        { active: selectedFilter === 'serious' },
      ]"
      @click="selectFilter('serious')"
    >
      Serious
    </button>
    <button
      :class="['filter-btn', 'urgent', { active: selectedFilter === 'urgent' }]"
      @click="selectFilter('urgent')"
    >
      Urgent
    </button>
  </div>
</template>

<script>
export default {
  emits: ["onFilterChange"],
  data() {
    return {
      selectedFilter: "all",
    };
  },
  methods: {
    selectFilter(filter) {
      this.selectedFilter = filter;
      this.$emit("onFilterChange", filter);
    },
  },
};
</script>

<style lang="scss" scoped>
@use "assets/stylesheets/noteFilter.scss";
</style>
