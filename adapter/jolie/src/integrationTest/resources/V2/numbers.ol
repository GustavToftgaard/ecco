from console import Console
from string_utils import StringUtils

type twoArguments { 
  m:int 
  n:int 
}

interface NumbersAPI { 
  requestResponse: 
    sumUpTo( int )( int ),
    sumBetween( twoArguments )( int ),
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

    [ sumUpTo( n )( response ) {
      for( i = 0, i < n, i++ ) {
        response += i
      }
    } ]

    [ sumBetween( request )( response ) {
      m -> request.m
      n -> request.n

      upToN = n * ( n - 1 ) / 2
      upToM = m * ( m - 1 ) / 2
      response = upToN - upToM

      if( m < n ) {
        response = upToN - upToM
      } else {
        response = upToM - upToN
      }
    } ]

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

service Test {
  embed Console as console
  embed StringUtils as stringUtils
  embed Numbers as numbers

  main {
    // sumUpTo
    sumUpTo@numbers( 5 ) ( sumUpToRes1 )
    println@console( "sumUpTo Expected 10: " + sumUpToRes1 ) ()

    // sumBetween
    sumBetween@numbers( { m = 2 n = 7 } ) ( sumBetweenRes1 )
    println@console( "sumBetween Expected 20: " + sumBetweenRes1 ) ()
    sumBetween@numbers( { m = 7 n = 2 } ) ( sumBetweenRes1 )
    println@console( "sumBetween Expected 20: " + sumBetweenRes1 ) ()

    // factorial
    factorial@numbers( 5 ) ( factorialRes1 )
    println@console( "factorial Expected 120: " + factorialRes1 ) ()

    // fibonacci
    fibonacci@numbers( 6 ) ( fibonacciRes1 )
    println@console( "fibonacci Expected 8: " + fibonacciRes1 ) ()

  }
}