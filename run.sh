#
# Starts the eventproxy container
#

docker run --restart always -d -p 8081:8081 --name eventproxy eventproxy

# EOF
