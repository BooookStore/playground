module Main where
import System.Environment (lookupEnv)

main :: IO ()
main = do
  content <- lookupEnv "USER_INFORMATION_FILE" >>= mapM readFile

  case content of
    Just c -> putStrLn $ "user-information\n" ++ c
    Nothing -> putStrLn "ERROR: user-information can't read"

  return ()
