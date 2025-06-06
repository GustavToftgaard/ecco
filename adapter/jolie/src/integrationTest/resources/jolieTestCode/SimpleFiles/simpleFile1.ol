from console import Console
from string_utils import StringUtils

type twoArguments {
  m:int
  n:int
}

interface NumbersAPI {
  requestResponse:
    sumUpTo( int )( int ),
    sumBetween( twoArguments )( int )
}

service Numbers {
  execution:sequential

  inputPort numbersInput {
    location: "local"
    interfaces: NumbersAPI
  }

  main {

    [ sumUpTo( n )( response ) {
      ...
    } ]

    [ sumBetween( request )( response ) {
      ...
    } ]

  }
}