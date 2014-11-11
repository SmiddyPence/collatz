var collatzApp = angular.module('collatzApp', ['ngRoute']);

collatzApp.config(function($routeProvider) {
	$routeProvider
		.when('/', {
			templateUrl : 'pages/home.html',
			controller  : 'mainController'
		})

        .when('/booting-up', {
            templateUrl : 'pages/booting-up.html',
            controller  : 'bootingUpController'
        })

		.when('/example1', {
			templateUrl : 'pages/example.html',
			controller  : 'exampleController'
		})

		.when('/example2', {
			templateUrl : 'pages/example.html',
			controller  : 'exampleController2'
		});
});

collatzApp.controller('mainController', function($scope) {
	$scope.message = 'Everyone come and see how good I look!';
});

collatzApp.controller('bootingUpController', function($scope) {
    $scope.message = 'Everyone come and see how good I look!';
});

collatzApp.controller('exampleController', function($scope) {
    $scope.message = 'Check out the circular paths in the Collatz Conjecture';
    $.get( "http://collatz.herokuapp.com/circular-paths", function( data ) {
        var width = $(document).width() - 30;
        var height = $(document).height();

        var g = new Graph();
        g.edgeFactory.template.style.directed = true;

        for(i=0; i<data.length; i++){
            g.addNode(data[i].id, {label: "" + data[i].value});
        }

        for(i=0; i<data.length; i++){
            if(data[i].nextNumber.id !== undefined){
                console.log(data[i].nextNumber)
                g.addEdge(data[i].id, data[i].nextNumber.id);
            }
        }

        var layouter = new Graph.Layout.Spring(g);
        var renderer = new Graph.Renderer.Raphael('canvas', g, width, height);
    });
});

collatzApp.controller('exampleController2', function($scope) {
	$scope.message = 'Check out the longest path in this Collatz Conjecture';
    $.get( "http://collatz.herokuapp.com/longest-resolved-path", function( data ) {
        var width = $(document).width() - 30;
        var height = $(document).height();
        var g = new Graph();
        g.edgeFactory.template.style.directed = true;

        for(i=0; i<data.length-1; i++){
            g.addEdge(data[i].value, data[i+1].value);
        }

        var layouter = new Graph.Layout.Ordered(g, topological_sort(g));
        var renderer = new Graph.Renderer.Raphael('canvas', g, width, height);
    });
});