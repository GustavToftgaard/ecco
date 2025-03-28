from console import Console
from string_utils import StringUtils

type twoArguments { 
  m:int 
  n:int 
}

type rangeArguments { 
  start?:int 
  stop:int 
  step?:int
}

type rangeResponse { list*:int }

interface NumbersAPI { 
  requestResponse: 
    sumUpTo( int )( int ),
    sumBetween( twoArguments )( int ),
    factorial( int )( int ),
    fibonacci( int )( int ),
    triangular( int )( int ),
    hexagonal( int )( int ), 
    range( rangeArguments )( rangeResponse )
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

    [ range( request )( response ) {
      if( !is_defined( request.start ) )
        request.start = 0
      if( !is_defined( request.step ) )
        request.step = 1
      if( request.step > 0 ) {
        for( i = request.start, i < request.stop, i += request.step ) {
          list[#list] = i
        }
      } else {
        for( i = request.start, i < request.stop, i -= request.step ) {
          list[#list] = i
        }
      }
      
      response.list -> list
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

    // triangular
    triangular@numbers( 5 ) ( triangularRes1 )
    println@console( "triangular Expected 15: " + triangularRes1 ) ()

    // hexagonal
    hexagonal@numbers( 5 ) ( hexagonalRes1 )
    println@console( "hexagonal Expected 45: " + hexagonalRes1 ) ()

    // range
    range@numbers( { start = 0 stop = 12 step = 2 } ) ( rangeRes1 )
    println@console( "range Expected [ 0 2 4 6 8 10 ]: " ) ()
    println@console( valueToPrettyString@stringUtils( rangeRes1 ) )()

    range@numbers( { stop = 12 step = 2 } ) ( rangeRes1 )
    println@console( "range Expected [ 0 2 4 6 8 10 ]: " ) ()
    println@console( valueToPrettyString@stringUtils( rangeRes1 ) )()

    range@numbers( { start = 0 stop = 6 } ) ( rangeRes1 )
    println@console( "range Expected [ 0 1 2 3 4 5 ]: " ) ()
    println@console( valueToPrettyString@stringUtils( rangeRes1 ) )()

  }
}