module Main where
import System.Environment (lookupEnv)

main :: IO ()
main = do
  userInformationFilePath <- lookupEnv "USER_INFORMATION_FILE"
  case userInformationFilePath of
    Just path -> do
      content <- readFile path
      putStrLn $ "content: \n" ++ content
    Nothing -> putStrLn "ERROR: USER_INFORMATION_FILE not defined in environment variable"
