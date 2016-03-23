describe('details',function(){
    var scope;
    beforeEach(angular.mock.module('first'));
    beforeEach(angular.mock.inject(function($scope, $ionicModal, $location, $state, $http){
 scope=$rootScope.$new();
        $controller('details',{$scope:scope});
        
    }));
    
    it('should have variable text = "Test case"', function(){
        expect(scope.text).toBe('Test Case is here');
    });
    
});