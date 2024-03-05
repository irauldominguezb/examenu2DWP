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
        <h4 class="text-center">Catálogos más vendidos</h4>
      </template>
      <div>
        <b-row>
        <b-col cols="12" class="d-flex justify-content-between align-items-center mb-3 w-100">
          <b-dropdown variant="outline-primary" id="dropdown-1" text="Ordenamientos " class="m-md-2">
            <b-dropdown-item @click="() => sorting('author')">Autor</b-dropdown-item>
            <b-dropdown-item @click="() => sorting('publication')">Fechas</b-dropdown-item>
            <b-dropdown-item>Imagenes</b-dropdown-item>
          </b-dropdown>
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
              <b-col cols="12" lg="4" v-for="(book, index) in books" :key="index">
                <b-card  draggable @dragstart="drag($event, book)" :title="book.name" img-src="https://picsum.photos/1024/400/?image=25" img-alt="Image" img-top class="mb-4 p-2 shadow-lg hover card-style">
                    <b-card-text>
                      <p><b>Autor: </b>{{book.author}}</p>
                    </b-card-text>
                    <b-card-text>
                      <p><b>Publicacion: </b>{{new Date(book.publication).toLocaleDateString()["split"]("T")[0]}}</p>
                    </b-card-text>
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
            <b-icon icon="trash" flip-h class="text-secondary" scale="3"></b-icon>
          </div>
          <div 
            class="actions"
            @dragover.prevent
            @dragenter.prevent
            @drop="onDropToUpdate($event)"
          >
            <b-icon icon="pencil" class="text-secondary" scale="3"></b-icon>
          </div>
        </b-col>
      </b-row>
    </b-card>
    <SaveExampleVue
      @getBooks="getBooks"
    />
    <UpdateExampleVue
      :updateBook="updateBook"
      @getBooks="getBooks"
    />
  </div>
</template>
<script>
import SaveExampleVue from './SaveExample.vue';
import services from "@/services/Book"
import UpdateExampleVue from './UpdateExample.vue';
export default {
  components:{
    SaveExampleVue,
    UpdateExampleVue
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
      books: [],
      updateBook: {}
    };
  },
  methods:{
    showSaveModal(){
            this.$bvModal.show("modal-save-object");
    },
    showUpdateModal(data){
            this.$bvModal.show("modal-update-object");
            this.updateBook = data;

    },
    drag(event, obj){
            event.dataTransfer.dropEffect = "move";
            event.dataTransfer.effectAllowed = "move";
            event.dataTransfer.setData("objId", obj.id);
            event.dataTransfer.setData("objName", obj.name);
            event.dataTransfer.setData("objAuthor", obj.author);
            event.dataTransfer.setData("objPublication", obj.publication);
    },
    onDrop(event) {
            const itemID = event.dataTransfer.getData("objId")
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
                   const response = await services.deleteBook(itemID)
                    if(response.data){
                      this.$swal.fire({
                     title: "¡Eliminación exitosa!",
                     icon: "success",
                     showCancelButton: false,
                     showConfirmButton: false,
                     timer: 3000
                    }).then(() =>{
                      this.getBooks()
                    })
                  }
                } 
            })
    },
    onDropToUpdate(evt) {
            this.updateBook = {
              id: evt.dataTransfer.getData("objId"),
              name: evt.dataTransfer.getData("objName"),
              author: evt.dataTransfer.getData("objAuthor"),
              publication: evt.dataTransfer.getData("objPublication")
            }
            this.$bvModal.show("modal-update-object");
            
    },
    async getBooks(){
      try {
        const response = await services.getBook()
        this.books = response;
      } catch (error) {
        console.error("",error);
      }
    },
    async sorting(type){
      const response = await services.getBook({sort: type})
      this.books = response;
    }
  },
  mounted(){
      this.getBooks()
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
@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

.card-style{
    animation: fadeIn 0.5s ease-in-out;
    height: 240px;
}
</style>