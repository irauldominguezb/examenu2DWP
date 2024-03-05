<template>
    <b-modal
      id="modal-update-object" no-close-on-backdrop centered no-close-on-esc size="md"
    >
    <template v-slot:modal-header="{}">
          <h5>Actualizar libro</h5>
      </template>
      <b-row>
          <b-col cols="12" lg="6" class="mb-1">
              <b-form-group
                  id="fieldset-name"
                  invalid-feedback="Ingresa un título válido para el libro"
              >
                  <label class="required-field">Título: </label>
                  <b-form-input
                      type="text"
                      v-model="v$.book.name.$model"
                      :state="v$.book.name.$dirty ? !v$.book.name.$error : null"
                  ></b-form-input>
              </b-form-group>
          </b-col>
          <b-col cols="12" lg="6">
              <b-form-group
                  id="fieldset-author"
                  invalid-feedback="Ingresa un autor válido para el libro"
              >
                  <label for="input-author" class="required-field">Autor:</label>
                  <b-form-input
                      id="input-author"
                      type="text"
                      v-model="v$.book.author.$model"
                      :state="v$.book.author.$dirty ? !v$.book.author.$error : null"
                  ></b-form-input>
              </b-form-group>
          </b-col>
      </b-row>
      <b-row>
          <b-col cols="12">
              <b-form-group
                  id="fieldset-publication"
                  invalid-feedback="Ingresa una fecha válida para el libro"
              >   
                  <label class="required-field">
                      Publicación:
                  </label>
                  <b-form-input
                      type="date"
                    v-model="v$.book.publication.$model"
                    :state="v$.book.publication.$dirty ? !v$.book.publication.$error : null"
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
                      @click="closeModalSave"
                  >
                      Cancelar
                  </b-button>
                  <b-button 
                      variant="primary"
                      @click="toUpdateBook"
                  >
                      Actualizar
                  </b-button>
              </b-col>
          </b-row>
      </template>
    </b-modal>
  </template>
  

<script>
import { useVuelidate } from '@vuelidate/core'
import { required } from '@vuelidate/validators'
import services from "@/services/Book"
import moment from "moment"
export default {
    name: "UpdateExample",
    setup () {
        return { v$: useVuelidate() }
    },
    props: {
        updateBook: {
            type: Object,
            required: true
        }
    },
    data(){
        return {
            book: {
                name: "",
                author: "",
                publication: "",
                img: ""
            }
        }
    },
    methods:{
        closeModalSave(){
            this.$bvModal.hide("modal-update-object");
        },
        async toUpdateBook(){
            try {
                const response = await services.update(this.book);
                if(response.status === 200){
                    this.$swal.fire({
                        title: "Libro actualizado",
                        icon: "success",
                        showConfirmButton: false,
                        timer: 1500
                    });
                    this.$emit("getBooks");
                    this.$bvModal.hide("modal-update-object");
                }
            } catch (error) {
                console.log(error);
            }
        },
        formatted(date){
            return new Date(date).toString()
        },
    },
    validations: {
        book: {
            name: { required },
            author: { required },
            publication: { required }
        }
    },
    watch:{
        updateBook(oldBook, newBook){
            oldBook.publication = moment(newBook.publication).format("YYYY-MM-DD");
            this.book = oldBook;
        }
    }
}
</script>

<style>

</style>