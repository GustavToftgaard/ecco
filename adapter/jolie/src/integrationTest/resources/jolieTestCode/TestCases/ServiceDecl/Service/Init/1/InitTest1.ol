init {
    // database connection configuration
    config.database << {
      username = ""
      password = ""
      host = ""
      database = "file:db.sqlite"
      driver = "sqlite"
    }
  }