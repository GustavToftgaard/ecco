from console import Console
from string_utils import StringUtils

type twoArguments {
  m:int
  n:int
}

interface NumbersAPI {
  requestResponse:
    ...
}

service Numbers {
  execution:sequential

  inputPort numbersInput {
    ...
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
