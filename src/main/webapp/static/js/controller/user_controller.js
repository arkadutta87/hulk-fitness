'use strict';

angular.module('myApp').controller('UserController', ['$scope', 'UserFactory', function($scope, UserFactory) {
    var self = this;
    self.user={
        id:null,
        username:'',
        address:'',
        email:''
    };

    self.users=[];

    self.submit = submit;
    self.edit = edit;
    self.remove = remove;
    self.reset = reset;


    fetchAllUsers();

    function fetchAllUsers(){
        UserFactory.fetchAllUsers()
            .success(function(data){
                console.log(JSON.stringify(data));
                self.users = data;
            })
            .error(function(error){
                console.log('Error while fetching Users - the error description - '+error );
            });
            /*.then(
            function(d) {
                self.users = d;
            },
            function(errResponse){
                console.error('Error while fetching Users');
            }
            */
    }

    function createUser(user){
        UserFactory.createUser(user)
            .success(function(data){
               //console.log(JSON.stringify(data));
               //self.users = d;
               fetchAllUsers();
            })
            .error(function(error){
               console.log('Error while creating User - the error description - '+error );
            });
            /*.then(
            fetchAllUsers,
            function(errResponse){
                console.error('Error while creating User');
            }
        );*/
    }

    function updateUser(user, id){
        UserFactory.updateUser(user, id)
            .success(function(data){
                           //console.log(JSON.stringify(data));
                           //self.users = d;
                           fetchAllUsers();
                        })
                        .error(function(error){
                           console.log('Error while updating User - the error description - '+error );
                        });
            /*.then(
            fetchAllUsers,
            function(errResponse){
                console.error('Error while updating User');
            }
        );*/
    }

    function deleteUser(id){
        UserFactory.deleteUser(id)
            .success(function(data){
                                       //console.log(JSON.stringify(data));
                                       //self.users = d;
                                       fetchAllUsers();
                                    })
            .error(function(error){
                                       console.log('Error while deleting User - the error description - '+error );
                                    });
    }

    function submit() {
        if(self.user.id===null){
            console.log('Saving New User', self.user);
            createUser(self.user);
        }else{
            updateUser(self.user, self.user.id);
            console.log('User updated with id ', self.user.id);
        }
        reset();
    }

    function edit(id){
        console.log('id to be edited', id);
        for(var i = 0; i < self.users.length; i++){
            if(self.users[i].id === id) {
                    self.user = angular.copy(self.users[i]);
                break;
            }
        }
    }

    function remove(id){
        console.log('id to be deleted', id);
        if(self.user.id === id) {//clean form if the user to be deleted is shown there.
            reset();
        }
        deleteUser(id);
    }


    function reset(){
        self.user={id:null,username:'',address:'',email:''};
        $scope.myForm.$setPristine(); //reset Form
    }

}]);