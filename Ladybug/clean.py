import os, shutil

# Windows denies access if folder is read-only, so change perms
# https://stackoverflow.com/questions/2656322/shutil-rmtree-fails-on-windows-with-access-is-denied
def onerror(func, path, exc_info):
   """
   Error handler for ``shutil.rmtree``.

   If the error is due to an access error (read only file)
   it attempts to add write permission and then retries.

   If the error is for another reason it re-raises the error.
   
   Usage : ``shutil.rmtree(path, onerror=onerror)``
   """
   import stat
   # Is the error an access error?
   if not os.access(path, os.W_OK):
      os.chmod(path, stat.S_IWRITE)
      func(path)
   else:
      raise
	 
folder = '.\\'
for the_file in os.listdir(folder):
	file_path = os.path.join(folder, the_file, 'app\\build')
	try:
		if os.path.isdir(file_path): 
			print('Deleting', file_path)
			shutil.rmtree(file_path, onerror=onerror)
			# os.system('rmdir /S /Q "{}"'.format(file_path))
	except Exception as e:
		print(e)

