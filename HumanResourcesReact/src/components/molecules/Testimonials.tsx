import React, { useEffect, useRef, useState } from 'react';
import Card from '@mui/material/Card';
import CardHeader from '@mui/material/CardHeader';
import CardContent from '@mui/material/CardContent';
import Avatar from '@mui/material/Avatar';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import Container from '@mui/material/Container';
import Grid from '@mui/material/Grid2';
import { useTheme } from '@mui/system';
import { useDispatch, useSelector } from 'react-redux';
import { AppDispatch, RootState } from '../../store';
import { fetchGetCommentList } from '../../store/future/commentSlice';
import { useNavigate } from 'react-router-dom';

const logoStyle = {
  width: '64px',
  opacity: 0.3,
};

export default function Testimonials() {
  const theme = useTheme();
  const { comments, page, size } = useSelector((state: RootState) => state.comment);
  const dispatch = useDispatch<AppDispatch>();
  const isFetching = useRef(false);
  const navigate = useNavigate();

    const goUserStories = () => {
        navigate('/userstories');
    }
  useEffect(() => {
    if (!isFetching.current ) {
      isFetching.current = true;
      dispatch(fetchGetCommentList({ page, size }))
        .then(() => isFetching.current = false)
        .catch(() => isFetching.current = false);
    }
  }, [dispatch, page, size]);
  // Sadece ilk 3 yorumu göstermek için comments listesini slice ile sınırlıyoruz
  const limitedComments = comments.slice(0, 3);

  return (
    <Container
      id="testimonials"
      sx={{
        pt: { xs: 4, sm: 12 },
        pb: { xs: 8, sm: 16 },
        position: 'relative',
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        gap: { xs: 3, sm: 6 },
      }}
    >
      <Box
        sx={{
          width: { sm: '100%', md: '60%' },
          textAlign: { sm: 'left', md: 'center' },
        }}
      >
        <Typography
          component="h2"
          variant="h4"
          gutterBottom
          sx={{ color: 'text.primary' }}
        >
         Müşteri Yorumları
        </Typography>
        
        <Typography variant="body1" sx={{ color: 'text.secondary' }}>
        Müşterilerimizin ürünlerimiz hakkında neleri sevdiğini görün. Verimlilik, dayanıklılık ve memnuniyet konusunda nasıl üstün olduğumuzu keşfedin. Kalite, yenilik ve güvenilir destek için bize katılın.
        </Typography>
      </Box>
      <Grid container spacing={2}>
        {limitedComments.map((comment, index) => (
          <Grid size={{ xs: 12, sm: 6, md: 4 }} key={index} sx={{ display: 'flex' }}>
            <Card
              variant="outlined"
              sx={{
                display: 'flex',
                flexDirection: 'column',
                justifyContent: 'space-between',
                flexGrow: 1,
              }}
            >
              <CardContent>
                <Typography
                  variant="body1"
                  gutterBottom
                  sx={{ color: 'text.secondary' }}
                >
                  {comment.content}
                </Typography>
              </CardContent>
              <Box
                sx={{
                  display: 'flex',
                  flexDirection: 'row',
                  justifyContent: 'space-between',
                }}
              >
                <CardHeader
                  avatar={
                    <Avatar alt={comment.companyManagerFirstName} src={comment.companyManagerAvatar || "/static/images/avatar/default.jpg"} />
                  }
                  title={comment.companyManagerFirstName}
                  subheader={comment.companyName}
                />
                
              </Box>
            </Card>
            
          </Grid>
          
        ))}
      </Grid>
      <div>
      <button className="btn " style={{backgroundColor: 'lightblue' }} onClick={goUserStories}>Daha Fazla Yorum Görmek için Tıklayın</button>
      </div>
    </Container>
  );
}
