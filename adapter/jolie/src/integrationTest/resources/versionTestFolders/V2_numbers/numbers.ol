from console import Console
from string_utils import StringUtils

interface NumbersAPI { 
  requestResponse:
    factorial( int )( int ),
    fibonacci( int )( int )
}

service Numbers {
  execution:sequential

  inputPort numbersInput {
    location: "local"
    interfaces: NumbersAPI
  }

  main {

    [ factorial( n )( response ) {
      response = n
      for( i = n - 1, i > 0, i-- ) {
        response *= ( n - i )
      }
    } ]
    
    [ fibonacci( n )( response ) {
      response = 1
      pre = 0
      while( n > 1 ) {
        temp = response
        response = pre + response
        pre = temp
        n--
      }
    } ]
  }
}
