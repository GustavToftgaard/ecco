service Test {
  embed TestImport(1) in TestOutputPort
  embed MyService( { .protocol = "sodep", .factor = 2 } ) in Service
}