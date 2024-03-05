<template>
  <b-modal
    id="modal-save-object" no-close-on-backdrop centered no-close-on-esc size="md"
  >
  <template v-slot:modal-header="{}">
        <h5>Nuevo objeto</h5>
    </template>
    <b-row>
        <b-col cols="12">
            <b-form-group>
                <b-form-input
                    type="text"
                ></b-form-input>
            </b-form-group>
        </b-col>
    </b-row>
    <template slot="modal-footer">
        <b-row>
            <b-col 
                cols="12" 
                class="justify-content-center"
            >
                <b-button 
                    class="m-1"
                >
                    Cancelar
                </b-button>
                <b-button 
                    variant="primary"
                >
                    Registrar
                </b-button>
            </b-col>
        </b-row>
    </template>
  </b-modal>
</template>

<script>
export default {
    name: 'SaveExample',
    methods:{
        closeModalSave(){
            this.$bvModal.hide("modal-save-object");
        }
    }
}
</script>

<style>

</style>