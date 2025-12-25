<template>
    <div :class="['note', newNoteStatus]">
        <input
          class="new-note-title"
          placeholder="Enter note title..."
          v-model="newNoteTitle"
          @keyup.enter="createNewNote"
        >
        
        <div class="status-select">
            <div>
                <input type="radio" id="unimportant" v-model="newNoteStatus" value="unimportant" />
                <label for="unimportant">Unimportant</label>
            </div>

            <div>
                <input type="radio" id="serious" v-model="newNoteStatus" value="serious" />
                <label for="serious">Serious</label>
            </div>

            <div>
                <input type="radio" id="urgent" v-model="newNoteStatus" value="urgent" />
                <label for="urgent">Urgent</label>
            </div>
        </div>

        <!-- 
          We bind the native "click" event to the method createNewNote, 
          see: https://vuejs.org/guide/essentials/event-handling.html
        -->
        <button class="create-btn" @click="createNewNote">Create new note</button>
    </div>
</template>



<script>
import { HOST } from './config.js'
import { sendMessage } from './services/errorService.js'

export default {
  data() {
    return {
      newNoteStatus: 'unimportant',
      newNoteTitle: ''
    }
  },
  emits: ['onNoteCreated'],
  methods: {
    createNewNote() {
      if (!this.newNoteTitle.trim()) {
        return
      }

      fetch(`${HOST}/notes`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          title: this.newNoteTitle,
          status: this.newNoteStatus
        })
      })
        .then(res => {
          if (!res.ok) throw new Error('Failed to create note')
          return res.json()
        })
        .then(newNote => {
          this.$emit('onNoteCreated', newNote)
          this.newNoteTitle = ''
          this.newNoteStatus = 'unimportant'
        })
        .catch(err => sendMessage(err.message))
    }
  }
}

</script>



<style lang="scss" scoped>
@use 'sass:color';
@use 'assets/stylesheets/colors.scss' as appColors;
@use 'assets/stylesheets/noteCard.scss';

$gutter-size: 15px;


.note {
  @include noteCard.noteCard;

    & > input {
        padding: 15px 5px 15px 5px;
        border: 0;
        font-size: 20px;
        width: 100%;
        border-radius: 5px;

        &::placeholder {
            font-style: italic;
            color: white;
        }
    }

    & > .create-btn {
        padding: 10px 5px 10px 5px;
        width: 100%;
        background-color: white;
        color: appColors.$dark-green;
        font-weight: bold;
        font-size: 15px;
    }

    &.unimportant {
      & > input {
        background-color: appColors.$light-green;
        color: color.adjust(appColors.$dark-green, $blackness: 20%);
      }
    }

    &.serious {
      & > input {
        background-color: appColors.$light-yellow;
        color: color.adjust(appColors.$dark-yellow, $blackness: 20%);
      }
    }

    &.urgent {
      & > input {
        background-color: appColors.$light-red;
        color: color.adjust(appColors.$dark-red, $blackness: 20%);
      }
    }

    .status-select {
        display: flex;
        flex-wrap: wrap;
        gap: 10px;

        margin-top: 10px;
        margin-bottom: 10px;
        
        &  input {
            margin-right: 5px;
        }
    }
}

</style>
