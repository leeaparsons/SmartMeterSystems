import {createModalObj, get, passwordsMatch, post, processResponse, setUpMessageModal} from "./utils.js";

const fortressNavbar = document.createElement('div');

fortressNavbar.innerHTML = `
<nav class="navbar navbar-expand-md navbar-dark bg-dark">
    <div class=" nav_container">
      <!-- Navbar brand or logo -->
      <a class="navbar-brand" href="#">
        <img src="/images/logo.svg" alt="logo" class="logo" width="100"/>
      </a>

      <!-- Navbar toggler button for smaller screens -->
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>

      <!-- Navbar links -->
      <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
        <ul class="navbar-nav">
          <!-- Add other navigation links here if needed -->
          <li class="nav-item">
            <a class="nav-link active" href="/dashboardView/dashboard">Dashboard</a>
          </li>
          <li class="nav-item">
            <a class="nav-link active" href="/mapView/map">Map</a>
          </li>
          <li class="nav-item">
            <a class="nav-link active" href="/tableView/table">Table</a>
          </li>
        </ul>
        <!-- Greeting and User Icon -->
        <ul class="navbar-nav">
          <li class="nav-item dropdown">
            <a class="nav-link active dropdown-toggle nav-link_user" href="#" id="userDropdown" role="button" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
              Hello, {{authenticatedUser.name}}! <i class="fas fa-user"></i> <!-- User icon -->
            </a>
            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="userDropdown">
              <!-- Dropdown items for user actions -->
              <li><a class="dropdown-item" href="#" @click="showEditUserModal">Edit User Information</a></li>
              <li><a class="dropdown-item" href="#" @click="showChangePasswordModal">Change Password</a></li>
              <li><a class="dropdown-item" href="#" @click="logout">Log Out</a></li>
            </ul>
          </li>
        </ul>
      </div>
    </div>
  </nav>
`;

const editUserModal = document.createElement('div');
editUserModal.innerHTML =`<div class="modal fade" id="editUserModal" tabindex="-1" aria-labelledby="editUserModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header bg-primary text-white" data-bs-theme="dark">
          <h4 class="modal-title" id="editUserModalLabel">Edit User</h4>
          <button type="button" class="btn-close"  data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <form>
            <div class="mb-3">
              <label for="editName" class="form-label">Name:</label>
              <input type="text" class="form-control" placeholder="Enter name" required v-model="authenticatedUser.name">
            </div>
            
            <div class="d-flex justify-content-between">
              <button type="button" class="btn btn-success" @click="changeName">Save</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>`

const changePasswordModal = document.createElement('div');
changePasswordModal.innerHTML =`<div class="modal fade" id="changePasswordModal" tabindex="-1" role="dialog" aria-labelledby="changePasswordModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <div class="modal-header bg-primary text-white d-block">
        <h4 class="modal-title" id="changePasswordModalLabel">Change Password</h4>
      </div>
      <div class="modal-body">
        <form>
          <div class="form-group">
            <label for="oldPassword">Old Password</label>
            <input type="password" class="form-control" required v-model="authenticatedUser.oldPassword">
          </div>        
          
          <div class="form-group">
            <label for="newPassword">New Password</label>
            <input type="password" class="form-control" required v-model="authenticatedUser.password">
          </div>
          
          <div class="form-group">
            <label for="repeatPassword">Repeat Password</label>
            <input type="password" class="form-control" required v-model="repeatPassword">
          </div>
          
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
            <button type="button" class="btn btn-success" @click="changePassword">Change Password</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</div>`

document.getElementById('fortressNavbar').appendChild(fortressNavbar);
document.getElementById('fortressNavbar').appendChild(editUserModal);
document.getElementById('fortressNavbar').appendChild(changePasswordModal);

let messageModalObj;
let messageModalId = setUpMessageModal('fortressNavbar');

async function getNameOfAuthenticatedUser() {
    const endpoint = '/user/auth/getNameOfAuthenticatedUser';
    const successMessage = 'Name of current authenticated user retrieved successfully';

    let response = await get(endpoint);

    let status = await processResponse(response, successMessage);

    if (status.success)
        return response.json();

    return null;
}

async function changeName(authenticatedUserDTO) {
    const endpoint = '/user/auth/changeName';
    const successMessage = 'Name changed successfully';

    let response = await post(endpoint, authenticatedUserDTO);

    return await processResponse(response, successMessage, messageModalObj);
}

async function changePassword(authenticatedUserDTO){
    const endpoint = '/user/auth/changePassword';
    const successMessage = 'password changed successfully';

    let response = await post(endpoint, authenticatedUserDTO);

    return await processResponse(response, successMessage, messageModalObj);
}

function logout(){
    localStorage.removeItem('access_token');
    console.log('access token removed. \nLogout success');
    window.location.href ='login';
}

let changePasswordModalObj;
let editUserModalObj;

Vue.createApp({
    data() {
        return {
            message: '',
            authenticatedUser: {},
            repeatPassword:''
        };
    },

    methods: {
        logout(){
            logout();
        },
        showEditUserModal(){
            editUserModalObj.show();
        },

        showChangePasswordModal(){
            changePasswordModalObj.show();
        },

        async getNameOfAuthenticatedUser() {
            this.authenticatedUser = await getNameOfAuthenticatedUser();
        },

        async changeName(){
            const status = await changeName(this.authenticatedUser);
            this.message = status.message;
        },

        async changePassword(){
            if(passwordsMatch(this.authenticatedUser.password, this.repeatPassword)) {
                const status = await changePassword(this.authenticatedUser);
                this.message = status.message;

            }else {
                messageModalObj.show();
                this.message = 'Passwords do not match';
            }
        }
    },

    async created() {
        await this.getNameOfAuthenticatedUser();
    },

    mounted(){
        messageModalObj = createModalObj(messageModalId);
        editUserModalObj = createModalObj('editUserModal');
        changePasswordModalObj = createModalObj('changePasswordModal');
    }
}).mount('#fortressNavbar');
