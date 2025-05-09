from console import Console
from string_utils import StringUtils

interface NumbersAPI { 
  requestResponse:
    triangular( int )( int ),
    hexagonal( int )( int )
}

service Numbers {
  execution:sequential

  inputPort numbersInput {
    location: "local"
    interfaces: NumbersAPI
  }

  main {

    [ triangular( n )( response ) {
      response = 0
      var = 0
      while( var < n ) {
        response += ( var + 1 )
        var++
      }
    } ]

    [ hexagonal( n )( response ) {
      response = n * (2 * n - 1)
    } ]
  }
}
