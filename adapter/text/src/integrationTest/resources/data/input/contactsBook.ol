from console import Console
from string_utils import StringUtils
from file import File
// from runtime import Runtime

type contact {
  name: string( length( [1, 30] ) )
  number: string( regex( "[0-9]{8}" ) )
}

type contactReturn {
  contact: void | contact
}

type contactMod {
  name: string( length( [1, 30] ) )
  number: string( regex( "[0-9]{8}" ) )
  newName?: string( length( [1, 30] ) )
  newNumber?: string( regex( "[0-9]{8}" ) )
}

interface ContactsAPI {
  requestResponse:
    create( contact )( bool ),
    retrieve( string )( contactReturn ),
    delete( string )( bool ), //change to just string
    modify( contactMod )( bool ),
    save( string )( void ),
    load( string )( void ),

    // temp for test
    printContacts( void )( bool )
  
}

service Contacts {
  execution:sequential

  embed Console as console
  embed StringUtils as stringUtils
  embed File as file
  // embed Runtime as runtime

  inputPort ContactsInput {
    location: "local"
    interfaces: ContactsAPI
  }

  /* 
   * Used to create contacts both when the user wants to
   * create a contact and when loading in a contact book.
   * 
   * Arugments used:
   * name of contact ( request.name ) ( string )
   * number of contact ( request.number ) ( string )
   * 
   * Arguments returned:
   * contact added or not (request) ( bool )
  */
  define createContact {
    response = true

    // check if contact already in CB
    for ( element in global.contactsBook ) {
      if ( element.name == request.name ) {
        response = false
      }
    }

    // add new contact to CB
    if ( response ) {
      global.contactsBook.( global.numberOfContacts ).number = request.number
      global.contactsBook.( global.numberOfContacts ).name = request.name
      global.numberOfContacts++
    }
  }

  init {
    global.contactsBook = "Contact Book"
    global.numberOfContacts = 0
  }

  main {

    /*
     * Creates a contact in the contactBook if
     * contact with same name not already in contactBook.
     * 
     * Request: 
     * name: string( length( [1, 30] ) )
     * number: string( regex( "[0-9]{8}" ) )
     * 
     * Responce: 
     * bool
    */
    [ create( request )( response ) {
      createContact
    } ]

    /*
     * Retrives a contact if contact by
     * given name is in contactBook.
     * 
     * Request: 
     * string
     * 
     * Responce: 
     * contact: void
     *  OR
     * contact.name: string( length( [1, 30] ) )
     * contact.number: string( regex( "[0-9]{8}" ) )
    */
    [ retrieve( request )( response ) {
      foundContact = false
      for ( i = 0, i < global.numberOfContacts, i++ ) {
        if ( global.contactsBook.( i ).name == request ) {
          foundContact = true
          index = i
        }
      }

      if ( foundContact ) {
        response.contact.name = global.contactsBook.( index ).name
        response.contact.number = global.contactsBook.( index ).number
      } else {
        response.contact = void
      }
    } ]

    /*
     * Delets a contact if contact by
     * given name in contactBook.
     * 
     * Request: 
     * string
     * 
     * Responce: 
     * bool
    */
    [ delete( request )( response ) {
      response = false

      // find contact and delete it
      for ( i = 0, i < global.numberOfContacts, i++ ) {
        if ( global.contactsBook.( i ).name == request ) {
          response = true
          undef( global.contactsBook.( i ) )
        }
      }
    } ]

    /*
     * Modifies contact name and or number if 
     * contact is in contactBook.
     * 
     * Request: 
     * name: string( length( [1, 30] ) )
     * number: string( regex( "[0-9]{8}" ) )
     * newName?: string( length( [1, 30] ) )
     * newNumber?: string( regex( "[0-9]{8}" ) )
     * 
     * Responce: 
     * bool
    */
    [ modify( request )( response ) {
      response = false

      // check if contact given is in CB
      for ( i = 0, i < global.numberOfContacts, i++ ) {
        if ( global.contactsBook.( i ).name == request.name ) {
          foundContact = true
          index = i
        }
      }

      // modifies the requested attributes
      if( foundContact ) {
        response = true
        if( is_defined( request.newName ) ) {
          global.contactsBook.( index ).name = request.newName
        }
        if( is_defined( request.newNumber ) ) {
          global.contactsBook.( index ).number = request.newNumber
        }
      }
    } ]

    /*
     * Saves contactBook to a given file 
     * with given file name.
     * 
     * Request: 
     * string
     * 
     * Responce: 
     * void
    */
    [ save( request )( response ) {
      strOut.filename = request
      
      // seperate names and numbers, then add them to a string
      strOut.content = ""
      for ( i = 0, i < global.numberOfContacts, i++ ) {
        if ( is_defined( global.contactsBook.( i ) ) ) {
          strOut.content += global.contactsBook.( i ).name
          strOut.content += "\n"
          strOut.content += global.contactsBook.( i ).number
          strOut.content += "\n"
        }
      }

      // write string to file
      writeFile@file( strOut )( resW1 )
    } ]

    /*
     * Loads contactBook from a given file
     * by given file name.
     * 
     * Request: 
     * string
     * 
     * Responce: 
     * void
    */
    [ load( request )( response ) {
      // delete the current CB
      undef( global.contactsBook )
      global.numberOfContacts = 0

      strIn.filename = request

      // read from file
      readFile@file( strIn )( file )

      // create content used in while loop
      check = ""
      foundName = false
      i = 0
      length@stringUtils( file )( length )

      // loop through text from file and creating contacts in order
      while ( i < length ) {
        start = i

        // find next "\n"
        while ( check != "\n" ) {
          file.end = i + 1
          file.begin = i
          substring@stringUtils( file )( check )
          i++
        }
        check = ""

        // extract an atribute from the file
        file.end = i - 1
        file.begin = start
        substring@stringUtils( file )( check2 )

        // check to see if both name and number of a contact found
        if ( !foundName ) {
          foundName = true
          request.name = check2
        } else {
          request.number = check2
          createContact
          foundName = false
        }
        
      }
      response = void
    } ]

    // Used for testing
    [ printContacts( request )( response ) {
      println@console( valueToPrettyString@stringUtils( global.contactsBook ) )()
      response = true
    } ]

  }
}

service Test {
  embed Console as console
  embed StringUtils as stringUtils
  embed Contacts as contacts

  define testCreate {
    println@console( "Create: " )()

    con1.name = "Bob Myskins"
    con1.number = "12345678"
    create@contacts( con1 )( resCreate1 )
    println@console( resCreate1 )()
    con2.name = "Wade Minion"
    con2.number = "87654321"
    create@contacts( con2 )( resCreate2 )
    println@console( resCreate2 )()

    // hit name to long
    con3.name = "Wade Minion Wade Minion Wade Minion Wade Minion"
    con3.number = "87654321"
    resNameToLong = !( con3 instanceof contact )
    println@console( resNameToLong )()
  }

  define testRetrive {
    println@console( "\n" )()
    println@console( "Retrive: " )()

    // load in test CB
    load@contacts( "testCB1.txt" )(  )

    // test retrive contact in CB
    retrieve@contacts( "Bob Myskins" )( resRetrive1 )
    println@console( is_defined( resRetrive1.contact.name ))()

    retrieve@contacts( "Wade Minion" )( resRetrive2 )
    println@console( is_defined( resRetrive1.contact.name ))()

    // test retrive contact not in CB
    retrieve@contacts( "Hello World!" )( resRetrive3 )
    println@console( ! is_defined( resRetrive3.contact.name ))()
  }

  define testDelete {
    println@console( "\n" )()
    println@console( "Delete: " )()

    // load in test CB
    load@contacts( "testCB1.txt" )(  )

    // test delete contact in CB
    delete@contacts( "Bob Myskins" )( resDelete1 )
    println@console( resDelete1 )()
    retrieve@contacts( "Bob Myskins" )( resRetrive2 )
    tempDelet = ( resRetrive2.contact instanceof void)
    println@console( tempDelet )()

    // test delete contact not in CB
    delete@contacts( "Bob Myskins" )( resDelete2 )
    println@console( ! resDelete2 )()
  }

  define testModify {
    println@console( "\n" )()
    println@console( "Modify: " )()

    // load in test CB
    load@contacts( "testCB1.txt" )(  )

    // modify contact in CB
    retrieve@contacts( "Wade Minion" )( resModify1 )
    resModify1.contact.newName = "Wade Minion 777"
    resModify1.contact.newNumber = "43214321"

    modify@contacts( resModify1.contact )( resModify2 )
    println@console( resModify2 )()

    retrieve@contacts( "Wade Minion 777" )( resRetrive3 )
    println@console( is_defined( resRetrive3.contact.name ) )()

    retrieve@contacts( "Wade Minion" )( resRetrive4 )
    println@console( ! is_defined( resRetrive4.contact.name ) )()

    // modify contact not in CB
    modify@contacts( resModify1.contact )( resModify2 )
    println@console( ! resModify2 )()
  }

  define testSaveAndLoad {
    println@console( "\n" )()
    println@console( "Save And Load: " )()

    // Load
    load@contacts( "testCB2.txt" )(  )
    println@console( "true" )() // not sure of to check (TODO)

    // save
    save@contacts( "testCB2.txt" )(  )
    println@console( "true" )() // not sure of to check (TODO)
  }

  // Used from testing
  define printContacts {
    // load
    load@contacts( "testCB1.txt" )(  )

    // print
    printContacts@contacts(  )( res )
  }

  main {
    testCreate

    testSaveAndLoad

    testRetrive

    testDelete

    testModify

    // printContacts // Used for testing
  }
}