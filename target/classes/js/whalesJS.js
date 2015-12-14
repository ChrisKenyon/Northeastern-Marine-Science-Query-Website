var app = angular.module('savethewhales.app', ['ngResource']);

app.controller('whalesCtrl', function($scope, $http) {

    $scope.sites = [];
    $scope.locations = [];
    $scope.countries = [];
    $scope.states = [];
    $scope.types = [];
    $scope.exposures = [];
    $scope.zones = [];
    $scope.subzones = [];

    $scope.locationSelect = [];
    $scope.countrySelect = [];
    $scope.stateSelect = [];
    $scope.typeSelect = [];
    $scope.exposureSelect = [];
    $scope.zoneSelect = [];
    $scope.subzoneSelect = [];
    $scope.startDate = "";
    $scope.endDate = "";

    $( ".date" ).datepicker({
      changeYear: true,
    });


    $http({
        method: "GET",
        url: '/get/options',
        Accept: 'application/json'
    }).success(function (data) {
        $scope.sites = data.siteId;
        $scope.locations = data.locations;
        $scope.countries = data.countries;
        $scope.states = data.states;
        $scope.types = data.types;
        $scope.exposures = data.exposures;
        $scope.zones = data.zones;
        $scope.subzones = data.sub_zones;
    })

    $scope.download_csv = function(){
       var url = "/data/Request-" + new Date().getTime();
       $http({
            method: 'POST',
            url: url,
            data: JSON.stringify({locations: $scope.locationSelect,
                                  countries: $scope.countrySelect,
                                  states:    $scope.stateSelect,
                                  types:     $scope.typeSelect,
                                  exposures: $scope.exposureSelect,
                                  zones:     $scope.zoneSelect,
                                  sub_zones: $scope.subzoneSelect,
                                  start_date_time: $scope.startDate,
                                  end_date_time:   $scope.endDate }),
            headers: {
                    'Content-type': 'application/json',
                    'Accept': "text/csv; charset=utf-8"}
        }).success(function (data, status, headers, config) {
              var blob = new Blob([data], {type: "text/csv; charset=utf-8"});
              var objectUrl = URL.createObjectURL(blob);
              promptDownload(objectUrl);
              populatePre(url);
          }).error(function (data, status, headers, config) {
              alert("Error!");
              //upload failed
          });
    }
    function promptDownload(url) {
       window.location.assign(url);
    }
    function populatePre(url) {
        var xhr = new XMLHttpRequest();
        xhr.onload = function () {
            document.getElementById('query_contents').textContent = this.responseText;
        };
        xhr.open('GET', url);
        xhr.send();
    }
});
