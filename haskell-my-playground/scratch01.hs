import Control.Applicative

a1 = getZipList $ (+) <$> ZipList [1, 2, 3] <*> ZipList [100, 100, 100]
a2 = getZipList $ (+) <$> ZipList [1, 2, 3] <*> ZipList [100, 100..]
a3 = getZipList $ max <$> ZipList [1, 2, 3, 4, 5, 3] <*> ZipList [5, 3, 1, 2]
a4 = getZipList $ (,,) <$> ZipList "dog" <*> ZipList "cat" <*> ZipList "rat"
a5 = getZipList $ (,) <$> ZipList [1, 2, 3, 4, 5] <*> ZipList [5, 5, 5, 5, 5]