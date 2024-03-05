<template>
  <div>
    <div class="d-flex justify-content-center container">
      <div class="carousel-container">
        <b-carousel id="carousel-1" v-model="index" :interval="2000" controls indicators background="#ababab" fade>
          <b-carousel-slide v-for="(slide, i) in slides" :key="i" :img-src="slide.img" :caption="slide.caption"
            :text="slide.text"></b-carousel-slide>
        </b-carousel>
      </div>
    </div>
    <b-card class="container bg-transparent p-2 shadow">
      <template #header>
        <h4 class="text-left">Objetos</h4>
      </template>
      <div>
        <b-row>
        <b-col cols="12" class="d-flex justify-content-end align-items-center mb-3 w-100">
          <b-button
            variant="outline-primary"
            class="d-flex align-items-center"
            @click="showSaveModal()"
          >
            <b-icon icon="plus"></b-icon>
          </b-button>
        </b-col>
      </b-row>
      </div>
      <b-row>
        <b-col cols="12" lg="9">
            <b-row>
              <b-col cols="12" lg="4" v-for="(obj, index) in objects" :key="index">
                <b-card draggable @dragstart="drag($event, obj, index)" title="Card Title" img-src="https://picsum.photos/1024/400/?image=25" img-alt="Image" img-top class="mb-4 p-2 shadow-lg">
                    <b-card-text>
                      {{obj.description}}
                    </b-card-text>
                    <template #footer>
                      <div class="d-flex justify-content-center p-1 align-items-center">
                        <b-button variant="outline-primary" class="mr-2 button-style"><b-icon icon="pencil"></b-icon></b-button>
                      </div>
                    </template>
                </b-card>
              </b-col>
            </b-row>
        </b-col>
        <b-col cols="12" lg="3">
          <div 
            class="actions"
            @dragover.prevent
            @dragenter.prevent
            @drop="onDrop($event)"
          >
            <label class="text-secondary">¡Suelta aquí para eliminar!</label>
          </div>
          <div 
            class="actions"
            @dragover.prevent
            @dragenter.prevent
            @drop="onDrop($event)"
          >
            <label class="text-secondary">¡Suelta aquí para editar!</label>
          </div>
        </b-col>
      </b-row>
    </b-card>
    <SaveExampleVue/>
  </div>
</template>
<script>
import SaveExampleVue from './SaveExample.vue';
export default {
  components:{
    SaveExampleVue
  },
  data() {
    return {
      index: 0,
      slides: [
        {
          img: 'https://picsum.photos/1024/400/?image=52',
          caption: 'Primer Slide',
          text: 'Este es el primer slide',
        },
        {
          img: 'https://picsum.photos/1024/400/?image=54',
          caption: 'Segundo Slide',
          text: 'Este es el segundo slide',
        },
        {
          img: 'https://picsum.photos/1024/400/?image=58',
          caption: 'Tercer Slide',
          text: 'Este es el tercer slide',
        }
      ],
      objects: [
        {
          id:1,
          name: 'Objeto 1',
          description: 'Descripcion del objeto 1'

        },
        {
          id:2,
          name: 'Objeto 2',
          description: 'Descripcion del objeto 2'

        },
        {
          id:3,
          name: 'Objeto 3',
          description: 'Descripcion del objeto 3'
        }
      ]
    };
  },
  methods:{
    showSaveModal(){
            this.$bvModal.show("modal-save-object");
    },
    drag(event, obj){
            event.dataTransfer.dropEffect = "move";
            event.dataTransfer.effectAllowed = "move";
            event.dataTransfer.setData("objId", obj.id);
    },
    
    onDrop(evt) {
            const itemID = evt.dataTransfer.getData("objId")
            this.$swal.fire({
                title: '¿Eliminar objeto permanentemente?',
                text: "Si lo eliminas no podrás recuperarla",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Sí, eliminar',
                cancelButtonText: 'Cancelar'
            }).then(async (result)=>{
                 if(result.isConfirmed){
                   this.$swal.fire({
                     title: "¡Eliminación exitosa!",
                     icon: "success",
                     showCancelButton: false,
                     showConfirmButton: false,
                     timer: 3000
                   })
                } 
            })
        }
  }
};
</script>

<style scoped>
.carousel-container {
    height: 370px !important; 
    width: 900px;
}

.actions{
  width: 100%; 
  height: 290px; 
  border: 2px dashed #ccc;
  margin-bottom: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.button-style{
  height: 40px; 
  width: 70px;
   margin-right: 10px;
}
</style>